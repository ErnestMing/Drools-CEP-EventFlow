package cn.com.fusio.quartz.job;

import cn.com.fusio.datasource.es.ESQueryService;
import cn.com.fusio.datasource.es.common.QueryMode;
import cn.com.fusio.drools.DroolsService;
import cn.com.fusio.event.EventEnrich;
import cn.com.fusio.event.merge.PinganEduPVEnrich;
import cn.com.fusio.event.raw.PinganBehaviorData;
import cn.com.fusio.quartz.common.QuartzJobParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.core.util.StringUtils;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * 定时 quartz Job ：
 *      1.查询 ES 行为数据 --- PP || (PP,PV)
 *      2.数据转换：PinganBehaviorData --> PinganEduPVEnrich
 *      3.Drools 规则触发
 *
 * created by zyming in 2017/8/18
 */
public class PinganBDETJob implements Job{

    private static Logger logger = LogManager.getLogger(PinganBDETJob.class) ;

    public PinganBDETJob(){}

    /**
     * 通过 Quartz Job 设置的 JobDatMap 给变量赋值
     * @param jobDataMap
     * @return
     */
    public int memberVarInit ( JobDataMap jobDataMap ){

        String queryMode = jobDataMap.getString(QuartzJobParameter.QUERY_MODE) ;
        String timeSpan = jobDataMap.getString(QuartzJobParameter.TIME_SPAN) ;
        String startTime = jobDataMap.getString(QuartzJobParameter.START_TIME) ;
        String endTime = jobDataMap.getString(QuartzJobParameter.END_TIME) ;

        // 1.判断ES query mode是否为空，如果为空直接跳出
        if(StringUtils.isEmpty(queryMode)){
            return 1 ;
        } else {
            // 2.判断 query mode 是否为 now-n(d,m,s)模式
            if(QueryMode.RECENT_PERIOD == queryMode ){
                if(StringUtils.isEmpty(timeSpan)){
                    return 2 ;
                }
            }
            // 2.判断 query mode 是否为startTime-endTime模式
            if(QueryMode.FIXED_PERIOD == queryMode ){
                if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(endTime)){
                    return 2 ;
                }
            }
        }
        return 0 ;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info("----> quartz's PinganBDETJob start...") ;

        logger.info("----> quartz's PinganBDETJob start to acquire JobParameter...") ;
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
    // ------------------------------------------------------------------- 参数获取 Start -------------------------------------------------------------
        // 1.获取 drools规则引擎参数
        logger.info("----> quartz's PinganBDETJob start to acquire drools's parameter...") ;
        String factType = jobDataMap.getString(QuartzJobParameter.FACT_TYPE);
        if(StringUtils.isEmpty(factType)){
            logger.info("----> quartz's PinganBDETJob terminates unsuccessfully...") ;
            return;
        }
        String sessionName = jobDataMap.getString(QuartzJobParameter.KIESESSION_NAME);
        String agendaGroupName = jobDataMap.getString(QuartzJobParameter.AGENDA_GROUP_NAME);
        String entryPointName = jobDataMap.getString(QuartzJobParameter.ENTRY_POINT_NAME);
        logger.info("[sessionName="+sessionName+" ,agendaGroupName="+agendaGroupName+" ,entryPointName="+entryPointName+"]");

        // 2.获取 es 查询参数
        logger.info("----> quartz's PinganBDETJob start to acquire elasticsearch's parameter...") ;
        String queryMode = jobDataMap.getString(QuartzJobParameter.QUERY_MODE) ;
        if(StringUtils.isEmpty(queryMode)) {
            logger.info("----> quartz's PinganBDETJob terminates unsuccessfully...") ;
            return;
        }
        String queryTemplate = jobDataMap.getString(QuartzJobParameter.QUERY_TEMPLATE) ;
        String timeSpan = jobDataMap.getString(QuartzJobParameter.TIME_SPAN) ;
        String startTime = jobDataMap.getString(QuartzJobParameter.START_TIME) ;
        String endTime = jobDataMap.getString(QuartzJobParameter.END_TIME) ;

    // ------------------------------------------------------------------- 参数获取 End -------------------------------------------------------------
    //-------------------------------------------------------------------- 数据转换 Start ------------------------------------------------------------
        // 3.查询数据、触发规则
        logger.info("----> quartz's PinganBDETJob start to query ElasticSearch...") ;
        ESQueryService esSearchQuery = new ESQueryService();
        List<PinganBehaviorData> dataList = null ;
        // 3.1.查询平安教化行为数据
        if( queryMode == QueryMode.RECENT_PERIOD ){
            // 指定查询的数据:timespan
            dataList = esSearchQuery.queryPinganDataByTimeSpan(timeSpan,queryTemplate);
        } else if( queryMode == QueryMode.FIXED_PERIOD ){
            // 指定查询的数据:startTime-endTime
            dataList = esSearchQuery.queryPinganDataByRange(startTime,endTime, queryTemplate) ;
        }

        // 3.2.事件 Enrich
        List<PinganEduPVEnrich> enrichedEventList = new EventEnrich().enrichPinganEduPV(dataList);
    //-------------------------------------------------------------------- 数据转换 End ------------------------------------------------------------
        // 3.3.触发规则
        logger.info("----> quartz's PinganBDETJob start to insert fact..") ;
        DroolsService.cepFactInsertAndFire(enrichedEventList,sessionName,agendaGroupName,entryPointName);

        enrichedEventList = null ;

        logger.info("----> quartz's PinganBDETJob terminates successfully ...") ;
    }

}
