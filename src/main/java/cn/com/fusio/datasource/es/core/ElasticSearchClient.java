package cn.com.fusio.datasource.es.core;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ElasticSearch 访问客户端 单例模式
 * created by zyming in 2017/8/10
 */
public class ElasticSearchClient {

    private static String clusterName = "fusiondata" ;
    private static String[] hosts = {"172.31.0.70" , "172.31.9.106" , "172.31.4.150"} ;
    private static int port = 9300 ;

    // Transport 客户端 属性参数
    private static Settings settings = Settings.builder()
            .put("cluster.name",clusterName)
            .put("client.transport.sniff", true)
            .put("client.transport.ping_timeout","5s")
            .put("client.transport.nodes_sampler_interval","5s")
            .build() ;

    private ElasticSearchClient(){ }

    /**
     * 获取 ElasticSearch 客户端 ,此处不使用单例，性能问题，有可能多个定时任务同时触发
     * @return
     */
    public static TransportClient getEsClient(){
        // 不使用 单例模式
        TransportClient client = initClient();
        return client ;
    }

    /**
     * 初始化 ElasticSearch 客户端
     * @return
     */
    private static TransportClient initClient(){
        TransportClient client = null ;
        try {
            // 1.设置 settings
            client = new PreBuiltTransportClient(settings) ;
            // 2.设置 ES 主机地址
            for(int i = 0 ; i < hosts.length ; i++){
                client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(hosts[i]),port)) ;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } finally {
            return client ;
        }
    }
}
