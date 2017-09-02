package cn.com.fusio.event.merge;

import cn.com.fusio.event.BaseEvent;
import cn.com.fusio.utils.UrlUtil;

import java.util.Date;

/**
 * @Description: 平安教化-表单-PV 事件
 * @Author : Ernest
 * @Date : 2017/8/21 11:35
 */
public class PinganEduFormPVEvent extends BaseEvent {

    private String app_id ;
    private String domain_userid ;
    private String domain_sessionid ;
    private String domain_sessionidx ;
    private String event_name ;
    private String page_urlhost ;
    private String page_url ;
    private String page_urlquery ;
    private String geo_country ;
    private String geo_region_name ;
    private String geo_city ;
    private String br_name ;
    private String dvce_type ;
    private Date collector_tstamp ;

    private String articleId ;
    private String projid ;
    private String expid ;
    private String channel ;
    private String taskId ;
    private String userid ;
    private String destAddr ;


    public PinganEduFormPVEvent(){}

    public PinganEduFormPVEvent(String app_id, String domain_userid, String domain_sessionid, String domain_sessionidx, String event_name, String page_urlhost, String page_url, String page_urlquery, String geo_country, String geo_region_name, String geo_city, String br_name, String dvce_type, Date collector_tstamp ) {
        this.app_id = app_id;
        this.domain_userid = domain_userid;
        this.domain_sessionid = domain_sessionid;
        this.domain_sessionidx = domain_sessionidx;
        this.event_name = event_name;
        this.page_urlhost = page_urlhost;
        this.page_url = page_url;
        this.page_urlquery = page_urlquery;
        this.geo_country = geo_country;
        this.geo_region_name = geo_region_name;
        this.geo_city = geo_city;
        this.br_name = br_name;
        this.dvce_type = dvce_type;
        this.collector_tstamp = collector_tstamp;

        // 对应 DRL文件中，@timestamp
        this.eventTime = collector_tstamp ;
        super.setEventTime(collector_tstamp);

        //解析字段
        this.articleId = UrlUtil.parseParameterValue(page_urlquery,"articleId=") ;
        if(null == this.articleId){
            this.articleId = UrlUtil.parseParameterValue(page_urlquery,"formId=") ;
        }
        this.projid = UrlUtil.parseParameterValue(page_urlquery,"projid=") ;
        this.expid = UrlUtil.parseParameterValue(page_urlquery,"expid=") ;
        this.channel = UrlUtil.parseParameterValue(page_urlquery,"channel=") ;
        this.taskId = UrlUtil.parseParameterValue(page_urlquery,"taskid=") ;
        this.userid = UrlUtil.parseParameterValue(page_urlquery,"userid=") ;
        this.destAddr = UrlUtil.parseParameterValue(page_urlquery,"destAddr=") ;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getDomain_userid() {
        return domain_userid;
    }

    public void setDomain_userid(String domain_userid) {
        this.domain_userid = domain_userid;
    }

    public String getDomain_sessionid() {
        return domain_sessionid;
    }

    public void setDomain_sessionid(String domain_sessionid) {
        this.domain_sessionid = domain_sessionid;
    }

    public String getDomain_sessionidx() {
        return domain_sessionidx;
    }

    public void setDomain_sessionidx(String domain_sessionidx) {
        this.domain_sessionidx = domain_sessionidx;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getPage_urlhost() {
        return page_urlhost;
    }

    public void setPage_urlhost(String page_urlhost) {
        this.page_urlhost = page_urlhost;
    }

    public String getPage_url() {
        return page_url;
    }

    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }

    public String getPage_urlquery() {
        return page_urlquery;
    }

    public void setPage_urlquery(String page_urlquery) {
        this.page_urlquery = page_urlquery;
    }

    public String getGeo_country() {
        return geo_country;
    }

    public void setGeo_country(String geo_country) {
        this.geo_country = geo_country;
    }

    public String getGeo_region_name() {
        return geo_region_name;
    }

    public void setGeo_region_name(String geo_region_name) {
        this.geo_region_name = geo_region_name;
    }

    public String getGeo_city() {
        return geo_city;
    }

    public void setGeo_city(String geo_city) {
        this.geo_city = geo_city;
    }

    public String getBr_name() {
        return br_name;
    }

    public void setBr_name(String br_name) {
        this.br_name = br_name;
    }

    public String getDvce_type() {
        return dvce_type;
    }

    public void setDvce_type(String dvce_type) {
        this.dvce_type = dvce_type;
    }

    public Date getCollector_tstamp() {
        return collector_tstamp;
    }

    public void setCollector_tstamp(Date collector_tstamp) {
        this.collector_tstamp = collector_tstamp;
    }

    public String getArticleId() {
        return articleId;
    }

    public void setArticleId(String articleId) {
        this.articleId = articleId;
    }

    public String getProjid() {
        return projid;
    }

    public void setProjid(String projid) {
        this.projid = projid;
    }

    public String getExpid() {
        return expid;
    }

    public void setExpid(String expid) {
        this.expid = expid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDestAddr() {
        return destAddr;
    }

    public void setDestAddr(String destAddr) {
        this.destAddr = destAddr;
    }

    @Override
    public String toString() {
        return "PinganEduFormPVEvent{" +
                "app_id='" + app_id + '\'' +
                ", domain_userid='" + domain_userid + '\'' +
                ", domain_sessionid='" + domain_sessionid + '\'' +
                ", domain_sessionidx='" + domain_sessionidx + '\'' +
                ", event_name='" + event_name + '\'' +
                ", page_urlhost='" + page_urlhost + '\'' +
                ", page_url='" + page_url + '\'' +
                ", page_urlquery='" + page_urlquery + '\'' +
                ", geo_country='" + geo_country + '\'' +
                ", geo_region_name='" + geo_region_name + '\'' +
                ", geo_city='" + geo_city + '\'' +
                ", br_name='" + br_name + '\'' +
                ", dvce_type='" + dvce_type + '\'' +
                ", collector_tstamp=" + collector_tstamp +
                ", articleId='" + articleId + '\'' +
                ", projid='" + projid + '\'' +
                ", expid='" + expid + '\'' +
                ", channel='" + channel + '\'' +
                ", taskId='" + taskId + '\'' +
                ", userid='" + userid + '\'' +
                ", destAddr='" + destAddr + '\'' +
                '}';
    }
}
