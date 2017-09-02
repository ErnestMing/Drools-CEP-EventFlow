package cn.com.fusio.quartz.core;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Description:  单例模式创建 Scheduler
 * @Author : Ernest
 * @Date : 2017/8/20 19:23
 */
public class QuartzSchedulerFactory {

    /**
     * 执行调度器
     */
    private static Scheduler scheduler ;
    /**
     * quartz 文件位置
     */
    private static final String confFile = "quartz.properties"  ;

    private QuartzSchedulerFactory(){}

    /**
     * 获取 scheduler 调度器
     * @return
     */
    public static Scheduler getScheduler () {

        if(null == scheduler){
            StdSchedulerFactory schedulerFactory = null;
            try {
                schedulerFactory = new StdSchedulerFactory(confFile);
                scheduler = schedulerFactory.getScheduler() ;
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }

        return scheduler ;
    }
}
