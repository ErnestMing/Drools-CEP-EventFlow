package cn.com.fusio.datasource.es.core;

import cn.com.fusio.cache.RedisConstant;
import cn.com.fusio.event.raw.PinganBehaviorData;
import cn.com.fusio.utils.DateUtil;
import cn.com.fusio.utils.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.search.SearchHit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 解析查询到的ES数据到 Fact
 * created by zyming in 2017/8/17
 */
public class ESDataParser {

    private static Logger logger = LogManager.getLogger(ESDataParser.class) ;

    /**
     * Elasticsearch SearchHit[] 转换为  List<PinganBehaviorData>
     * @param hits
     * @return
     */
    public static List<PinganBehaviorData> hit2PinganArticlePV(SearchHit[] hits){

        // Drool带处理的 行为数据 列表
        List<PinganBehaviorData> partBDList = new ArrayList<PinganBehaviorData>() ;

        PinganBehaviorData behaviorData = null ;

        Object app_id = null ;
        Object domain_userid = null ;
        Object domain_sessionid = null ;
        Object domain_sessionidx = null ;
        Object event_name = null ;
        Object page_urlhost = null ;
        Object page_url = null ;
        Object page_urlquery = null ;
        Object geo_country = null ;
        Object geo_region_name = null ;
        Object geo_city = null ;
        Object br_name = null ;
        Object dvce_type = null ;
        Object collector_tstamp = null ;

        for(int i = 0 ;i < hits.length ;i++){

            SearchHit hit = hits[i];
            Map<String, Object> source = hit.getSource();

            app_id = source.get("app_id") ;
            domain_userid = source.get("domain_userid") ;
            domain_sessionid = source.get("domain_sessionid") ;
            domain_sessionidx = source.get("domain_sessionidx") ;
            event_name = source.get("event_name") ;
            page_urlhost = source.get("page_urlhost") ;
            page_url = source.get("page_url") ;
            page_urlquery = source.get("page_urlquery") ;
            geo_country = source.get("geo_country") ;
            geo_region_name = source.get("geo_region_name") ;
            geo_city = source.get("geo_city") ;
            br_name = source.get("br_name") ;
            dvce_type = source.get("dvce_type") ;
            collector_tstamp = source.get("collector_tstamp") ;

            behaviorData = new PinganBehaviorData(
                    app_id != null ? app_id.toString() : "",
                    domain_userid != null ? domain_userid.toString() : "",
                    domain_sessionid != null ? domain_sessionid.toString() : "",
                    domain_sessionidx != null ? domain_sessionidx.toString() : "",
                    event_name != null ? event_name.toString() : "",
                    page_urlhost != null ? page_urlhost.toString() : "",
                    page_url != null ? page_url.toString() : "",
                    page_urlquery != null ? page_urlquery.toString() : "",
                    geo_country != null ? geo_country.toString() : "",
                    geo_region_name != null ? geo_region_name.toString() : "",
                    geo_city != null ? geo_city.toString() : "",
                    br_name != null ? br_name.toString() : "",
                    dvce_type != null ? dvce_type.toString() : "",
                    collector_tstamp != null ? DateUtil.getDateFromStr(collector_tstamp.toString()) : null
            ) ;

            // 1.数据过滤:pingan-pv-email： article+form
            if (ESDataFilter.isPinganFromEmail(behaviorData)) {
                partBDList.add(behaviorData) ;
            }
        }
        return partBDList ;
    }

}
