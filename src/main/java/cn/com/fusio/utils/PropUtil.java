package cn.com.fusio.utils;

import cn.com.fusio.datasource.db.core.DbPoolConnection;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:
 * @Author : Ernest
 * @Date : 2017/9/1 11:10
 */
public class PropUtil {

    /**
     *
     * @param clazz
     * @param configFile  eg./druid.properties
     * @return
     */
    public static Properties loadPropertyFile(Class clazz , String configFile) {

        Properties prop = null;
        InputStream configStream = null;

        try {
            if (null != configFile && !"".equals(configFile)) {
                // .class 的 根目录 是 src/main
                configStream = clazz.getResourceAsStream(configFile);
            }
            prop = new Properties();
            prop.load(configStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != configStream) {
                try {
                    configStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return prop;
    }

}
