package cn.com.fusio.quartz.job;

import cn.com.fusio.datasource.es.ESQueryService;
import cn.com.fusio.datasource.es.common.QueryMode;
import cn.com.fusio.drools.DroolsService;
import cn.com.fusio.event.EventEnrich;
import cn.com.fusio.event.merge.PinganEduPVEnrich;
import cn.com.fusio.event.raw.PinganBehaviorData;
import cn.com.fusio.quartz.common.QuartzJobParameter;
import cn.com.fusio.utils.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.core.util.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 *  Redis key 处理
 * created by zyming in 2017/8/31
 */
public class RedisKeyManageJob implements Job{

    private static Logger logger = LogManager.getLogger(RedisKeyManageJob.class) ;

    public RedisKeyManageJob(){}

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info("----> Start to clean Redis'cache !!");

        // 1.参数获取
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();

        String key = jobDataMap.getString("") ;

        String before31Str = DateTime.now(DateTimeZone.forID("Asia/Shanghai")).minusDays(31).toString("M:d");

        // 2.删除31天前 缓存的行为数据
        logger.info("----> delete HashKey:[pingan:bd],Field.contains: ["+before31Str+"]");
        RedisUtil.delHashFields("pingan:bd",before31Str);

        // 3.删除 前一天 触发的用户限制
        logger.info("----> delete SetKey:[triggered:user]");
        RedisUtil.delKey("triggered:user");

        logger.info("----> End to clean Redis'cache !!");

    }

}
