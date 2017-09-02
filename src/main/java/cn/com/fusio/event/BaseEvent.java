package cn.com.fusio.event;

import java.util.Date;

/**
 * 基础 Event 类
 * created by zyming in 2017/8/18
 */
public class BaseEvent {

    /**
     * 统一DRL文件中，event事件的@timestamp变量
     */
    public Date eventTime ;

    public BaseEvent(){}

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
}
