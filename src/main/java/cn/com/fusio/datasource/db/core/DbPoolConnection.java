package cn.com.fusio.datasource.db.core;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接池-单例
 * created by zyming in 2017/7/24
 */
public class DbPoolConnection implements Serializable{

    private static DbPoolConnection dbPool = null ;
    private static DruidDataSource dds = null ;

    // 初始化 DataSource
    static {
        Properties prop = loadPropertyFile("/druid.properties") ;
        try {
            dds = (DruidDataSource) DruidDataSourceFactory
                    .createDataSource(prop);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private DbPoolConnection(){}

    /**
     * 获取数据库连接池单例实例
     * @return
     */
    public static synchronized DbPoolConnection getInstance(){
        if(null == dbPool) {
            dbPool = new DbPoolConnection() ;
        }
        return dbPool ;
    }

    public DruidPooledConnection getConnection(){
        DruidPooledConnection conn = null ;
        try {
            conn = dds.getConnection() ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  conn ;
    }

    /**
     * 从 src 加载配置文件
     * @param configFile
     * @return
     */
    public static Properties loadPropertyFile(String configFile){

        Properties prop = null ;
        InputStream configStream = null ;

        try {
            if(null != configFile && !"".equals(configFile)){
                // DbPoolConnection.class 的 根目录 是 src/main
                configStream = DbPoolConnection.class.getResourceAsStream(configFile);
            }
            prop = new Properties() ;
            prop.load(configStream);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            if (null != configStream){
                try {
                    configStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return prop ;
    }


    public static void main(String[] args) {
        DbPoolConnection.loadPropertyFile("") ;
    }

}
