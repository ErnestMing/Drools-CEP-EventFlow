package cn.com.fusio.runner;

import cn.com.fusio.utils.PropUtil;

import java.util.Properties;

/**
 * @Description:
 * @Author : Ernest
 * @Date : 2017/9/1 11:05
 */
public class ApplicationConfig {

    private static Boolean isInitWorkMemory;
    private static Boolean isCacheBD;

    static {
        Properties props = PropUtil.loadPropertyFile(ApplicationConfig.class, "/application.conf");

        isInitWorkMemory = Boolean.parseBoolean(props.getProperty("init.workmemory")) ;
        isCacheBD = Boolean.parseBoolean(props.getProperty("cache.bd")) ;

    }

    public static Boolean getIsInitWorkMemory() {
        return isInitWorkMemory;
    }

    public static void setIsInitWorkMemory(Boolean isInitWorkMemory) {
        ApplicationConfig.isInitWorkMemory = isInitWorkMemory;
    }

    public static Boolean getIsCacheBD() {
        return isCacheBD;
    }

    public static void setIsCacheBD(Boolean isCacheBD) {
        ApplicationConfig.isCacheBD = isCacheBD;
    }

    public static void main(String[] args) {
        ApplicationConfig.setIsInitWorkMemory(false);
        System.out.println(ApplicationConfig.getIsInitWorkMemory());
    }


}
