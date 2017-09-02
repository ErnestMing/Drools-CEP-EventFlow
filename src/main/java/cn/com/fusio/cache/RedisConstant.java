package cn.com.fusio.cache;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: Redis Key 管理
 * @Author : Ernest
 * @Date : 2017/8/28 11:55
 */
public class RedisConstant {

    /**
     * 平安行为数据缓存
     */
    public static final String bd_key = "pingan:bd" ;
    /**
     * 平安用户数据缓存
     */
    public static final String user_key = "pingan:user" ;
    /**
     * 平安内容信息
     */
    public static final String article_key = "pingan:article" ;
    /**
     * 平安form信息
     */
    public static final String form_key = "pingan:form" ;

    /**
     * 今天已经发送过邮件的用户缓存
     */
    public static final String sent_lt_1_day_key = "triggered:user" ;


    /**
     * 组合 Pingan 行为数据中的 Key: month-day-channel-userid-timestamp
     * @param channel
     * @param userId
     * @param ts
     * @return
     */
    public static final String combineBDFieldName(String channel , String userId , Date ts){

        DateTime date = new DateTime(ts);
        int month = date.getMonthOfYear();
        int day = date.getDayOfMonth();

        return month+":"+day+":"+channel+":"+userId+":"+ts.getTime() ;
    }


    public static void main(String[] args) {
        Date date = new Date() ;
        System.out.println(date.getTime());
        System.out.println(new DateTime(date).monthOfYear().get());
        System.out.println(new DateTime(date).dayOfMonth().get());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(sdf.format(date));
    }


}
