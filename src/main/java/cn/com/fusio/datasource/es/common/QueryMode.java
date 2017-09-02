package cn.com.fusio.datasource.es.common;

/**
 * created by zyming in 2017/8/18
 */
public class QueryMode {
    /**
     * 最近一段时间,只需要制定最近多久 TimeSpan
     */
    public static String RECENT_PERIOD = "RECENT_PERIOD" ;
    /**
     * 固定一段时间,需要制定，startTime ， endTime
     */
    public static String FIXED_PERIOD = "FIXED_PERIOD" ;

}
