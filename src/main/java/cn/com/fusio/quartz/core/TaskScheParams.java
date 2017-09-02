package cn.com.fusio.quartz.core;

import org.quartz.Job;

import java.util.Map;

/**
 * @Description: 任务计划
 * @Author : Ernest
 * @Date : 2017/8/20 18:58
 */
public class TaskScheParams {

    /**
     * 对应的Job Class
     */
    private Class<? extends Job> jobClass ;
    /**
     * Job 名称
     */
    private String jobName ;
    /**
     * Job 组
     */
    private String jobGroup ;

    /**
     * 触发器名称
     */
    private String triggerName ;
    /**
     * 触发器所属组
     */
    private String triggerGroup ;

    /**
     * cron格式：1 * * * *
     */
    private String cronTime ;

    /**
     * JobDetail参数
     */
    private Map<? extends String,?> jobDataMap ;

    public TaskScheParams() { }

    public TaskScheParams(Class<? extends Job> jobClass, String jobName, String jobGroup, String triggerName, String triggerGroup, String cronTime, Map<? extends String, ?> jobDataMap) {
        this.jobClass = jobClass;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.triggerName = triggerName;
        this.triggerGroup = triggerGroup;
        this.cronTime = cronTime;
        this.jobDataMap = jobDataMap;
    }

    public Class<? extends Job> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends Job> jobClass) {
        this.jobClass = jobClass;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroup() {
        return triggerGroup;
    }

    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    public String getCronTime() {
        return cronTime;
    }

    public void setCronTime(String cronTime) {
        this.cronTime = cronTime;
    }

    public Map<? extends String, ?> getJobDataMap() {
        return jobDataMap;
    }

    public void setJobDataMap(Map<? extends String, ?> jobDataMap) {
        this.jobDataMap = jobDataMap;
    }

    @Override
    public String toString() {
        return "TaskScheParams{" +
                "jobName='" + jobName + '\'' +
                ", jobGroup='" + jobGroup + '\'' +
                ", triggerName='" + triggerName + '\'' +
                ", triggerGroup='" + triggerGroup + '\'' +
                ", cronTime='" + cronTime + '\'' +
                ", jobDataMap=" + jobDataMap +
                '}';
    }
}
