package cn.com.fusio.quartz;

import cn.com.fusio.quartz.core.QuartzSchedulerFactory;
import cn.com.fusio.quartz.core.TaskScheParams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.*;

import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;

/**
 * @Description: Quartz调度工具类
 * @Author : Ernest
 * @Date : 2017/8/20 19:03
 */
public class QuartzService {

    private static Logger logger = LogManager.getLogger(QuartzService.class) ;

    // JobDetail , Trigger , Scheduler (addJob , exec all jobs)

    //crontrigger

    // 添加 Job 到 Sceduler

    public static int addJob(TaskScheParams taskSche){

        if(null == taskSche){
            logger.info("----> 任务计划参数为空，添加JOb失败！");
            return -1 ;
        }

        // 1.构建 JobDetail
        JobDataMap jobDataMap = new JobDataMap() ;
        jobDataMap.putAll(taskSche.getJobDataMap());

        JobDetail jobDetail = newJob(taskSche.getJobClass())
                .withIdentity(taskSche.getJobName(),taskSche.getJobGroup())
                .usingJobData(jobDataMap)
                .build() ;

        // 2.构建 Trigger
        CronTrigger trigger = newTrigger()
                .withIdentity(taskSche.getTriggerName(),taskSche.getTriggerGroup())
                .withSchedule(cronSchedule(taskSche.getCronTime()))
                .build() ;

        // 3.添加
        Scheduler scheduler = QuartzSchedulerFactory.getScheduler();
        try {
            // scheduleJob：1.job、trigger校验 2.store job/trigger 3.通知Listener监听Job/trigger
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return  1 ;
    }

    /**
     * 单次立即执行
     * @param taskSche
     * @return
     */
    public static int addJobBySingle(TaskScheParams taskSche){

        if(null == taskSche){
            logger.info("----> 任务计划参数为空，添加JOb失败！");
            return -1 ;
        }

        // 1.构建 JobDetail
        JobDataMap jobDataMap = new JobDataMap() ;
        jobDataMap.putAll(taskSche.getJobDataMap());

        JobDetail jobDetail = newJob(taskSche.getJobClass())
                .withIdentity(taskSche.getJobName(),taskSche.getJobGroup())
                .usingJobData(jobDataMap)
                .build() ;

        // 2.构建 Trigger: repeate = repeateCount+1
        Trigger trigger = newTrigger()
                .withIdentity(taskSche.getTriggerName(),taskSche.getTriggerGroup())
                .startNow()
                .withSchedule(simpleSchedule().withRepeatCount(0))
                .build() ;

        // 3.添加
        Scheduler scheduler = QuartzSchedulerFactory.getScheduler();
        try {
            // scheduleJob：1.job、trigger校验 2.store job/trigger 3.通知Listener监听Job/trigger
            scheduler.scheduleJob(jobDetail,trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        return  1 ;
    }

    // 调度所有的 Job
    public static int startAllJob(){
        Scheduler scheduler = QuartzSchedulerFactory.getScheduler();
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            logger.info("----> scheduler 启动失败！");
            e.printStackTrace();
            return -1 ;
        }
        logger.info("----> scheduler 启动成功！");
        return 1 ;
    }

    // 关闭 scheduler
    public static int stopAllJob(){
        Scheduler scheduler = QuartzSchedulerFactory.getScheduler();
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            logger.info("----> scheduler shutdown 失败！");
            e.printStackTrace();
            return -1 ;
        }
        logger.info("----> scheduler shutdown 成功！");
        return 1 ;
    }



}
