package cn.com.fusio.quartz.common;

/**
 * Quartz定时器，参数统一命名类
 * created by zyming in 2017/8/18
 */
public class QuartzJobParameter {

    /**
     * 规则引擎触发：基本参数
     */
    // 事件类型
    public static String FACT_TYPE = "factType" ;
    // SessionName
    public static String KIESESSION_NAME = "sessionName";
    // 议程组
    public static String AGENDA_GROUP_NAME = "agendaGroupName" ;
    // CEP  Stream入口
    public static String ENTRY_POINT_NAME = "entryPointName";

    /**
     * ElasticSearch数据查询模板，需要的参数
     */
    // ES查询模板
    public static String QUERY_TEMPLATE = "queryTemplate" ;
    // ES查询模式
    public static String QUERY_MODE = "queryMode";

    // queryMode=RECENT_PERIOD时使用，即：查询最近一段时间的数据
    public static String TIME_SPAN = "timeSpan";

    // queryMode=FIXED_PERIOD时使用，即：查询指定一段时间内的数据
    public static String START_TIME = "startTime";
    public static String END_TIME = "endTime";



}
