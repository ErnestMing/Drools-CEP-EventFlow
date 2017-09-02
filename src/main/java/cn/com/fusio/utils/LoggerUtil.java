package cn.com.fusio.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Description: Log4j Logger
 * @Author : Ernest
 * @Date : 2017/8/30 14:30
 */
public class LoggerUtil {
    /**
     * 是否开启Debug
     */
    public static boolean isDebug =  LogManager.getLogger(LoggerUtil.class).isDebugEnabled();

    /**
     * Debug 输出
     * @param clazz  	目标.Class
     * @param message	输出信息
     */
    public static void debug(Class<? extends Object> clazz ,String message){
        if(!isDebug)return ;
        Logger logger = LogManager.getLogger(clazz);
        logger.debug(message);
    }
    /**
     * Debug 输出
     * @param clazz  	目标.Class
     * @param fmtString 输出信息key
     * @param value		输出信息value
     */
    public static void fmtDebug(Class<? extends Object> clazz,String fmtString,Object...value){
        if(!isDebug)return ;
        if(StringUtils.isBlank(fmtString)){
            return ;
        }
        if(null != value && value.length != 0){
            fmtString = String.format(fmtString, value);
        }
        debug(clazz, fmtString);
    }
    /**
     * Error 输出
     * @param clazz  	目标.Class
     * @param message	输出信息
     * @param e			异常类
     */
    public static void error(Class<? extends Object> clazz ,String message,Exception e){
        Logger logger = LogManager.getLogger(clazz);
        if(null == e){
            logger.error(message);
            return ;
        }
        logger.error(message, e);
    }
    /**
     * Error 输出
     * @param clazz  	目标.Class
     * @param message	输出信息
     */
    public static void error(Class<? extends Object> clazz ,String message){
        error(clazz, message, null);
    }
    /**
     * 异常填充值输出
     * @param clazz 	目标.Class
     * @param fmtString	输出信息key
     * @param e			异常类
     * @param value		输出信息value
     */
    public static void fmtError(Class<? extends Object> clazz,Exception e,String fmtString,Object...value){
        if(StringUtils.isBlank(fmtString)){
            return ;
        }
        if(null != value && value.length != 0){
            fmtString = String.format(fmtString, value);
        }
        error(clazz, fmtString, e);
    }
    /**
     * 异常填充值输出
     * @param clazz		目标.Class
     * @param fmtString 输出信息key
     * @param value		输出信息value
     */
    public static void fmtError(Class<? extends Object> clazz,
                                String fmtString, Object...value) {
        if(StringUtils.isBlank(fmtString)){
            return ;
        }
        if(null != value && value.length != 0){
            fmtString = String.format(fmtString, value);
        }
        error(clazz, fmtString);
    }

    /**
     * 打印 Info信息
     * @param clzName
     * @param msg
     */
    public static void info(String clzName , String msg){
        Logger logger = LogManager.getLogger(clzName);
        logger.info(msg);
    }
}
