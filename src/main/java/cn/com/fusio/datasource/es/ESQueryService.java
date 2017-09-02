package cn.com.fusio.datasource.es;

import cn.com.fusio.cache.RedisConstant;
import cn.com.fusio.datasource.es.core.ESDataParser;
import cn.com.fusio.datasource.es.core.ElasticSearchClient;
import cn.com.fusio.event.raw.PinganBehaviorData;
import cn.com.fusio.runner.ApplicationConfig;
import cn.com.fusio.utils.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.search.SearchHit;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 查询 ElasticSearch 中数据
 * created by zyming in 2017/8/10
 */
public class ESQueryService {

    private Logger logger = LogManager.getLogger(ESQueryService.class);

    private TransportClient client = null ;

    // scroll 查询时间间隔
    private TimeValue scrollInterval = TimeValue.timeValueMinutes(1) ;
    // scroll 每次查询的记录数
    private String scrollSize = "1000" ;


    public ESQueryService(){
        client = ElasticSearchClient.getEsClient() ;
        //设置默认的 index / tpye
        client.prepareIndex("snowplow","enriched") ;
    }

    /**
     * 检测 ElasticSearch 集群情况
     */
    public void checkClusterHealth() {

        ClusterAdminClient cluster = client.admin().cluster();
        ClusterHealthResponse health = cluster.prepareHealth().get();

        Map<String, ClusterIndexHealth> indices = health.getIndices();

        Set<Map.Entry<String, ClusterIndexHealth>> entries = indices.entrySet();

        for(Map.Entry<String,ClusterIndexHealth> entry :entries){
            String key = entry.getKey();
            ClusterIndexHealth value = entry.getValue();

            System.out.println("key:"+key+" health:"+value.getStatus().toString());
        }
    }

    /**
     * 根据时间范围查询数据
     * 使用 Inline 类型的 查询模板
     *  此种模式好处：
     *      不会侵入ElasticSearch的stat进行存储数据
     */
    public List<PinganBehaviorData> queryPinganDataByRange(String startTime , String endTime , String template ) {

        // 0.设置 index / type
//        client.prepareIndex("snowplow","enriched") ;

        // 1.创建查询模板请求
        // 1.1.参数设置
        Map<String,Object> templatePrams = new HashMap<String,Object>();
        templatePrams.put("startTime",startTime) ;
        templatePrams.put("endTime",endTime) ;
        templatePrams.put("secordSize",scrollSize) ;

        // 1.2.创建Scroll方式的查询请求
        SearchRequest searchReq = new SearchRequest().searchType(SearchType.DFS_QUERY_THEN_FETCH)
                .scroll(scrollInterval) ;

        // 2.通过模板请求查询数据
        SearchResponse scorllResp = new SearchTemplateRequestBuilder(client)
                .setScript(template)
                .setScriptType(ScriptType.INLINE)
                .setScriptParams(templatePrams)
                .setRequest(searchReq)
                .get()
                .getResponse() ;

        // 3.处理查询结果
        SearchHit[] hits = scorllResp.getHits().getHits();

        List<PinganBehaviorData> behaviorDataList = new ArrayList<PinganBehaviorData>() ;

        do {

            // 3.1.转换每个 scroll 查询到的数据
            List<PinganBehaviorData> partBDList = ESDataParser.hit2PinganArticlePV(hits);
            // 3.2.添加到总的行为 List 中
            behaviorDataList.addAll(partBDList) ;
            // 3.3.获取下一个 Scroll 查询的数据
            scorllResp = client.prepareSearchScroll(scorllResp.getScrollId()).setScroll(scrollInterval).execute().actionGet() ;

        }while( scorllResp.getHits().getHits().length != 0 );

        List<PinganBehaviorData> sortedList = dataCacheAndSort(behaviorDataList);

        return sortedList ;
    }

    /**
     * 查询最近一段时间的数据
     *      m:  分钟     eg.30m   最近30分钟
     *      h:  小时     eg.6h    最近6小时
     *      d:  天       eg.30d  最近30天
     * @param timeSpan 时间间隔
     * @return
     */
    public List<PinganBehaviorData> queryPinganDataByTimeSpan(String timeSpan , String template ){
        // 0.设置 index / type
//        client.prepareIndex("snowplow","enriched") ;

        // 1.创建查询模板请求
        // 1.1.参数设置
        Map<String,Object> templatePrams = new HashMap<String,Object>();
        templatePrams.put("timeSpan",timeSpan) ;
        templatePrams.put("recordSize",scrollSize) ;

        // 1.2.创建Scroll方式的查询请求
        SearchRequest searchReq = new SearchRequest().searchType(SearchType.DFS_QUERY_THEN_FETCH)
                .scroll(scrollInterval) ;

        // 2.通过模板请求查询数据
        SearchResponse scorllResp = new SearchTemplateRequestBuilder(client)
                .setScript(template)
                .setScriptType(ScriptType.INLINE)
                .setScriptParams(templatePrams)
                .setRequest(searchReq)
                .get()
                .getResponse() ;

        // 3.处理查询结果
        SearchHit[] hits = null ;

        List<PinganBehaviorData> behaviorDataList = new ArrayList<PinganBehaviorData>() ;

        do {
            hits = scorllResp.getHits().getHits();
            // 3.1.转换每个 scroll 查询到的数据
            List<PinganBehaviorData> partBDList = ESDataParser.hit2PinganArticlePV(hits);
            // 3.2.添加到总的行为 List 中
            behaviorDataList.addAll(partBDList) ;
            // 3.3.获取下一个 Scroll 查询的数据
            scorllResp = client.prepareSearchScroll(scorllResp.getScrollId()).setScroll(scrollInterval).execute().actionGet() ;
            // 3.4.获取记录数
            logger.info("----> scroll data size:   " + hits.length );

        }while( scorllResp.getHits().getHits().length != 0 );

        //关闭客户端
        client.close();
        logger.info("----> close elasticsearch client");

        List<PinganBehaviorData> sortedList = dataCacheAndSort(behaviorDataList);

        return sortedList ;
    }

    /**
     * 缓存行为数据，排序
     *      缓存行为数据：只是记录该行为数据是否插入到 WorkMemory中
     *      更严谨的方式，是在 insert fact to WorkMemory 中后，Cache 数据
     * @param behaviorDataList
     * @return
     */
    private List<PinganBehaviorData> dataCacheAndSort(List<PinganBehaviorData> behaviorDataList){

        logger.info("----> valid data size:   " + behaviorDataList.size() );
        // 行为数据缓存、排序
        List<PinganBehaviorData> pendingDataList = new ArrayList<PinganBehaviorData>() ;
        List<PinganBehaviorData> sortedList = null ;

        if( behaviorDataList != null && !behaviorDataList.isEmpty() ){

            if(ApplicationConfig.getIsCacheBD()){
                // 1.缓存数据到 ES
                behaviorDataList.forEach(behaviorData -> {
                    //行为数据key 中的 fieldName
                    String bdFieldName = RedisConstant.combineBDFieldName(behaviorData.getChannel(), behaviorData.getUserid(), behaviorData.getCollector_tstamp());

                    // 过滤出在 Redis 中不存在的行为数据
                    if(!RedisUtil.isExistInHash(RedisConstant.bd_key,bdFieldName)){
                        pendingDataList.add(behaviorData);
                        //添加到 Redis缓存
                        logger.info("redis: cache PinganBehaviorData to "+RedisConstant.bd_key+",fieldName="+bdFieldName);
                        // 行为数据的缓存，只是为了 防止重复插入数据到 Drools 的 WorkMemory 中
                        RedisUtil.saveObj2Hash(RedisConstant.bd_key,bdFieldName,null);
                    }else {
                        logger.info("redis exist key=["+RedisConstant.bd_key+",fieldName="+bdFieldName+"]");
                    }
                });
            }

            // 2.待处理数据排序
            if(pendingDataList != null && !pendingDataList.isEmpty()){
                //warn:列表数据排序+插入Fact：不能使用并行排序 parallseStream , 并行排序只会局部有序
                sortedList = pendingDataList.stream()
                        .sorted(new Comparator<PinganBehaviorData>() {
                            @Override
                            public int compare(PinganBehaviorData o1, PinganBehaviorData o2) {
                                return (int) (o1.getCollector_tstamp().getTime() - o2.getCollector_tstamp().getTime());
                            }
                        })
                        .collect(Collectors.toList());
            }

        }
        // 5.释放资源
        behaviorDataList = null ;
        if(sortedList != null && !sortedList.isEmpty()){
            logger.info("----> new data size:"+sortedList.size());
        }else {
            logger.info("----> new data size:"+0);
        }

        return sortedList ;
    }
}
