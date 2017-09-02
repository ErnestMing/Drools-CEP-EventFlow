package cn.com.fusio.drools;

import cn.com.fusio.drools.common.FactType;
import cn.com.fusio.drools.core.DroolsContext;
import cn.com.fusio.drools.core.RuleFireWay;
import cn.com.fusio.event.BaseEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Fact insert to Drools Work Memory ，and fire rule .
 *  1.Normal规则：
 *      参数：dataList , sessionName , entryPointName , agendaGroup
 *  2.CEP规则：
 *      参数：dataList , sessionName , agendaGroup
 * created by zyming in 2017/8/17
 */
public class DroolsService {

    private static Logger logger = LogManager.getLogger(DroolsService.class) ;

    /**
     *  KieSession Map , 保证每种类型的Session 只有一个实例
     */
    private static Map<String,KieSession> sessionMap = new ConcurrentHashMap<String,KieSession>() ;
    /**
     *  EntryPoint Map
     */
    private static Map<String,EntryPoint> entryPointMap = new ConcurrentHashMap<String,EntryPoint>() ;

    private DroolsService(){}

    /**
     * CEP 规则
     * @param sessionName
     * @param agendaGroup
     * @param entryPointName
     */
    private static void initKieSession(String sessionName ,String agendaGroup , String entryPointName){
        if(!sessionMap.containsKey(sessionName)){
            // 获取 Rule Session 入口
            KieSession kieSession = DroolsContext.build(sessionName);
            EntryPoint entryPoint = kieSession.getEntryPoint(entryPointName);

            // 保证每种类型的 Session 只有一个
            sessionMap.put(sessionName,kieSession) ;

            //key:sessionName+entrypointName    :   一个 Session 有多个 EntryPoint
            entryPointMap.put(genEntryPointKey(sessionName,entryPointName),entryPoint) ;
        }
    }

    /**
     * Normal 规则
     * @param sessionName
     * @param agendaGroup
     */
    private static void initKieSession(String sessionName, String agendaGroup ){
        if(!sessionMap.containsKey(sessionName)){
            // 获取 Rule Session 入口
            KieSession kieSession = DroolsContext.build(sessionName);

            // 保证每种类型的 Session 只有一个
            sessionMap.put(sessionName,kieSession) ;
        }
    }

    /**
     * 生成 EntryPoint 的 Key
     * @param sessionName
     * @param entryPoint
     * @return
     */
    private static String genEntryPointKey(String sessionName, String entryPoint){
        return sessionName+":"+entryPoint ;
    }

    /**
     *  Fact insert to KieSession
     *      适用于 所有 继承于 BaseEvent 的 Fact
     */
    public static void cepFactInsertAndFire(List<? extends BaseEvent> dataList , String sessionName , String agendaGroup , String entryPointName){

        //初始化，保存 KieSession ， EntryPoint 单例实例： 保证同类事件进入一个流中
        initKieSession(sessionName , agendaGroup , entryPointName) ;

        // 获取 Rule Session 入口
        KieSession kieSession = sessionMap.get(sessionName) ;

        if( dataList != null && !dataList.isEmpty() ){

            EntryPoint entryPoint = entryPointMap.get(genEntryPointKey(sessionName,entryPointName)) ;

            dataList.forEach(behaviorData -> {
                logger.info("----> insert Fact to drools , eventTime="+ behaviorData.eventTime);
                entryPoint.insert(behaviorData) ;
                // 触发规则
                RuleFireWay.basicFireRule(kieSession,agendaGroup);
            });

            dataList = null  ;
        }
    }

    /**
     *  CEP Fact insert to KieSession , but don't fire rule .
     */
    public static void cepFactInsertNotFire(List<? extends BaseEvent> dataList , String sessionName , String agendaGroup , String entryPointName){

        //初始化
        initKieSession(sessionName , agendaGroup , entryPointName) ;

        // 获取 Rule Session 入口
        KieSession kieSession = sessionMap.get(sessionName) ;

        if( dataList != null && !dataList.isEmpty() ){

            EntryPoint entryPoint = entryPointMap.get(genEntryPointKey(sessionName,entryPointName)) ;

            dataList.forEach(behaviorData -> {
                logger.info("----> insert Fact to drools , eventTime="+ behaviorData.eventTime);
                entryPoint.insert(behaviorData) ;
            });

            dataList = null  ;
        }
    }
}
