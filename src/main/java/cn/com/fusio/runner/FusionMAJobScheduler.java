package cn.com.fusio.runner;

import cn.com.fusio.datasource.es.ESQueryService;
import cn.com.fusio.datasource.es.EsSearchTemplates;
import cn.com.fusio.datasource.es.common.QueryMode;
import cn.com.fusio.datasource.es.common.TimeSpan;
import cn.com.fusio.drools.DroolsService;
import cn.com.fusio.drools.common.FactType;
import cn.com.fusio.event.EventEnrich;
import cn.com.fusio.event.merge.PinganEduPVEnrich;
import cn.com.fusio.event.raw.PinganBehaviorData;
import cn.com.fusio.quartz.QuartzService;
import cn.com.fusio.quartz.core.TaskScheParams;
import cn.com.fusio.quartz.common.QuartzJobParameter;
import cn.com.fusio.quartz.job.PinganBDETJob;
import cn.com.fusio.quartz.job.RedisKeyManageJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件流启动类：
 *      history-bd: startTime , endTime
 *      down-bd :   startTime , endTime
 * created by zyming in 2017/8/17
 */
public class FusionMAJobScheduler {

    private static Logger logger = LogManager.getLogger(FusionMAJobScheduler.class) ;

    public static void main(String[] args) {

        if(args.length != 4 ){
            System.out.println("history-bd: startTime , endTime\n" +
                    " *      down-bd :   startTime , endTime");
            System.exit(1);
        }

        String hyStartTime = args[0] ;      //历史数据：起始时间
        String hyEndTime= args[1] ;         //历史数据：截止时间
        String downStartTime = args[2] ;    //宕机：开始时间
        String downEndTime = args[3] ;      //宕机终止时间： 一般为 now

        //是否 初始化 Drools WorkMemory
        Boolean initWorkMemory = ApplicationConfig.getIsInitWorkMemory();

        if( initWorkMemory ){
            // 关闭 缓存 BD 行为数据 到 Redis
            ApplicationConfig.setIsCacheBD(false);

            // 1.系统重启时：Load 历史数据到 work memory ， 并不重复触发 UserInfo,ArticleInof,FormInfo的Cache操作
            loadData2WorkMemoryNotFire(hyStartTime,hyEndTime) ;

            // 2.开启 BD 缓存，关闭 初始化历史到WorkMemory
            ApplicationConfig.setIsInitWorkMemory(false);
            ApplicationConfig.setIsCacheBD(true);

            // 3.处理系统中断期间行为数据,并缓存 UserInfo,ArticleInfo,FormInfo to Redis
            loadData2WorkMemoryAndFire(downStartTime,downEndTime);

            // 4.启动 事件流
            ptc_recent_pv_data();
        } else {
            ptc_recent_pv_data();
        }

        //3.启动所有的 Quartz Job
        QuartzService.startAllJob() ;
    }

    /**
     * 近实时处理: pingan:pv 数据
     *
     */
    public static void ptc_recent_pv_data(){

        Map<String,Object> paramMap = new HashMap<String,Object>() ;
        // Drools 参数( fact_type , kessionName , agenda_group , entry_point)
        paramMap.put(QuartzJobParameter.FACT_TYPE,FactType.CEP) ;
//        paramMap.put(QuartzJobParameter.KIESESSION_NAME,"ks_pingan_breed_edu_pv") ;
        paramMap.put(QuartzJobParameter.KIESESSION_NAME,"ks_pingan_breed_edu_bd") ;
        paramMap.put(QuartzJobParameter.AGENDA_GROUP_NAME,"pingan_breed_edu_bd") ;
        paramMap.put(QuartzJobParameter.ENTRY_POINT_NAME,"pingan_breed_edu_bd") ;

        // ES 查询参数(query_mode , timespan , query_template)
        paramMap.put(QuartzJobParameter.QUERY_MODE, QueryMode.RECENT_PERIOD) ;
        paramMap.put(QuartzJobParameter.TIME_SPAN, TimeSpan.min_10) ;
        paramMap.put(QuartzJobParameter.QUERY_TEMPLATE, EsSearchTemplates.pingan_ts_pv_pp_edu()._2()) ;

        // 执行计划参数 (quartz cron: 秒，分，小时，天，月，星期)
        TaskScheParams taskScheParams =
                new TaskScheParams(PinganBDETJob.class, "job_pingan_breed_edu_pv", "pingan-job",
                        "job_pingan_breed_edu_pv", "pingan-trigger", "0 0/2 * * * ?", paramMap);

        QuartzService.addJob(taskScheParams) ;
        // 测试使用的是：startNow()
//        QuartzService.addJobBySingle(taskScheParams) ;
    }

    /**
     *  Init :workMemory ， 历史数据
     *   加载最近 30天 行为数据Event  到 WorkMemory
     *     ps:
     *      1.第一次启动，需要触发规则
     *      2.中断，宕机，重启，只需要加载到 WorkMemory
     */
    public static void loadData2WorkMemoryNotFire(String startTime , String endTime){
        // 3.查询数据、触发规则
        logger.info("----> init drools's WorkMemory !") ;
        ESQueryService esSearchQuery = new ESQueryService();
        List<PinganBehaviorData> dataList = null ;

        logger.info("----> query data in range[ 宕机之前一个月的行为数据 ]") ;
        // 3.1.查询平安教化行为数据
//        dataList = esSearchQuery.queryPinganDataByTimeSpan(TimeSpan.month_1,EsSearchTemplates.pingan_ts_pv_pp_edu()._2());
        dataList = esSearchQuery.queryPinganDataByRange(startTime,endTime,EsSearchTemplates.pingan_range_pv_pp_edu()._2()) ;

        // 3.2.事件 Enrich
        List<PinganEduPVEnrich> enrichedEventList = new EventEnrich().enrichPinganEduPV(dataList) ;
        //-------------------------------------------------------------------- 数据转换 End ------------------------------------------------------------
        // 3.3.触发规则
        logger.info("----> insert fact to WorkMemory !") ;
        DroolsService.cepFactInsertNotFire(enrichedEventList, "ks_pingan_breed_edu_bd", "pingan_breed_edu_bd", "pingan_breed_edu_bd");

        logger.info("----> quartz's PinganBDETJob terminates successfully ...") ;
    }

    /**
     * Load 宕机期间数据到 WorkMemory 并 缓存到 Redis
     * @param startTime
     * @param endTime
     */
    public static void loadData2WorkMemoryAndFire(String startTime , String endTime){
        // 3.查询数据、触发规则
        logger.info("----> init drools's WorkMemory !") ;
        ESQueryService esSearchQuery = new ESQueryService();
        List<PinganBehaviorData> dataList = null ;

        logger.info("----> query data in range[ 宕机时间段 ]") ;
        // 3.1.查询平安教化行为数据
//        dataList = esSearchQuery.queryPinganDataByTimeSpan(TimeSpan.month_1,EsSearchTemplates.pingan_ts_pv_pp_edu()._2());
        dataList = esSearchQuery.queryPinganDataByRange(startTime,endTime,EsSearchTemplates.pingan_range_pv_pp_edu()._2()) ;

        // 3.2.事件 Enrich
        List<PinganEduPVEnrich> enrichedEventList = new EventEnrich().enrichPinganEduPV(dataList);
        //-------------------------------------------------------------------- 数据转换 End ------------------------------------------------------------
        // 3.3.触发规则
        logger.info("----> insert fact to WorkMemory !") ;
        DroolsService.cepFactInsertAndFire(enrichedEventList, "ks_pingan_breed_edu_bd", "pingan_breed_edu_bd", "pingan_breed_edu_bd");

        logger.info("----> quartz's PinganBDETJob terminates successfully ...") ;
    }

/*
    *//**
     * 近实时处理: pingan:pv+pp 数据 最近30min
     *
     *//*
    public static void ptc_recent_pv_pp_data(){

        Map<String,Object> paramMap = new HashMap<String,Object>() ;
        // Drools 参数( fact_type , kessionName , agenda_group , entry_point)
        paramMap.put(QuartzJobParameter.FACT_TYPE,FactType.CEP) ;
        paramMap.put(QuartzJobParameter.KIESESSION_NAME,"ks_pingan_breed_edu_pv_pp") ;
        paramMap.put(QuartzJobParameter.AGENDA_GROUP_NAME,"pingan_breed_edu_pv_pp") ;
        paramMap.put(QuartzJobParameter.ENTRY_POINT_NAME,"pingan_breed_edu_pv_pp") ;

        // ES 查询参数(query_mode , timespan , query_template)
        paramMap.put(QuartzJobParameter.QUERY_MODE, QueryMode.RECENT_PERIOD) ;
        paramMap.put(QuartzJobParameter.TIME_SPAN, TimeSpan.hour_6) ;
        paramMap.put(QuartzJobParameter.QUERY_TEMPLATE, EsSearchTemplates.pingan_ts_pv_pp_edu()._2()) ;

        // 执行计划参数 (quartz cron: 秒，分，小时，天，月，星期)
        TaskScheParams taskScheParams =
                new TaskScheParams(PinganBDETJob.class, "job_pingan_breed_edu_pv_pp", "pingan-job",
                        "job_pingan_breed_edu_pv_pp", "pingan-trigger", "0 0 0 *//*24 * ?", paramMap);

//        QuartzService.addJob(taskScheParams) ;
        // 测试使用的是：startNow()
        QuartzService.addJobBySingle(taskScheParams) ;

    }*/



    public void cleanRedisCache(){

        // 执行计划参数 (quartz cron: 秒，分，小时，天，月，星期)
        TaskScheParams taskScheParams =
                new TaskScheParams(RedisKeyManageJob.class, "pingan_breed_cache_clean", "pingan-job",
                        "pingan_breed_cache_clean", "pingan-trigger", "0 0 1 * * ?", null);

//        QuartzService.addJob(taskScheParams) ;
        // 测试使用的是：startNow()
        QuartzService.addJobBySingle(taskScheParams) ;
    }


    /**
     * demo 测试
     */
    public static void recentData_demo(){

        Map<String,Object> paramMap = new HashMap<String,Object>() ;
        // Drools 参数
        paramMap.put(QuartzJobParameter.FACT_TYPE,FactType.CEP) ;
        paramMap.put(QuartzJobParameter.KIESESSION_NAME,"kession_pingan_breed_demo") ;
        paramMap.put(QuartzJobParameter.AGENDA_GROUP_NAME,"pingan_breed") ;
        paramMap.put(QuartzJobParameter.ENTRY_POINT_NAME,"pingan_breed") ;
        // ES 查询参数
        paramMap.put(QuartzJobParameter.QUERY_MODE, QueryMode.RECENT_PERIOD) ;
        paramMap.put(QuartzJobParameter.TIME_SPAN, TimeSpan.week_1) ;

        // 执行计划参数 (quartz cron: 秒，分，小时，天，月，星期)
        TaskScheParams taskScheParams =
                new TaskScheParams(PinganBDETJob.class, "pingan_breed_demo", "pingan",
                        "pingan_breed_demo", "trigger_demo", "0/30 * * * * ?", paramMap);

//        QuartzService.addJob(taskScheParams) ;
        // 测试使用的是：startNow()
        QuartzService.addJobBySingle(taskScheParams) ;
    }



}
