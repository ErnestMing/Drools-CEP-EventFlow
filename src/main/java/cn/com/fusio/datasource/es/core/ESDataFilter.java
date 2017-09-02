package cn.com.fusio.datasource.es.core;

import cn.com.fusio.event.raw.PinganBehaviorData;

/**
 * 数据校验+过滤：
 *      针对从 ES 中查询的数据进行过滤, ES查询不能完成的过滤规则
 *
 * created by zyming in 2017/8/16
 */
public class ESDataFilter {

    /**
     * 平安教化所有数据
     * 判断行为数据是否属于，从email打开访问FusionLife的平安用户行为 PV 记录
     * @param behaviorData
     * @return
     */
    public static Boolean isPinganFromEmail(PinganBehaviorData behaviorData){

        String page_urlquery = behaviorData.getPage_urlquery();

        boolean isValid = false ;

        isValid = (page_urlquery.contains("formId=") || page_urlquery.contains("articleId=")) &&
                page_urlquery.contains("userid=") &&
                page_urlquery.contains("destAddr=") &&
                page_urlquery.contains("channel=email") &&
                !page_urlquery.contains("currentUrl=");

        return isValid ;
    }

    /**
     * 是否是来自Email的，平安Article浏览数据
     * @param behaviorData
     * @return
     */
    public static Boolean isPinganArticleFromEmail(PinganBehaviorData behaviorData){
        String page_urlquery = behaviorData.getPage_urlquery();

        boolean isValid = false ;

        isValid = page_urlquery.contains("articleId=") &&
                page_urlquery.contains("userid=") &&
                page_urlquery.contains("destAddr=") &&
                page_urlquery.contains("channel=email") &&
                !page_urlquery.contains("currentUrl=");

        return isValid ;
    }

    /**
     * 是否是来自 Email的 平安 Form的浏览数据
     * @param behaviorData
     * @return
     */
    public static Boolean isPinganFormFromEmail(PinganBehaviorData behaviorData){
        String page_urlquery = behaviorData.getPage_urlquery();

        boolean isValid = false ;

        isValid = page_urlquery.contains("formId=") &&
                page_urlquery.contains("userid=") &&
                page_urlquery.contains("destAddr=") &&
                page_urlquery.contains("channel=email") &&
                !page_urlquery.contains("currentUrl=");

        return isValid ;
    }

}
