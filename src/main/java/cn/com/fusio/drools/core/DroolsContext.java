package cn.com.fusio.drools.core;

import cn.com.fusio.utils.LoggerUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.conf.ConstraintJittingThresholdOption;

/**
 * Drools 执行引擎
 * created by zyming in 2017/8/11
 */
public class DroolsContext {

    /**
     * Kie Service 上层接口
     */
    private static KieServices kieServices = null ;
    /**
     * container 用来加载 moudule.xml
     */
    private static KieContainer kieContainer = null ;

    private Logger logger = LogManager.getLogger(DroolsContext.class) ;

    private DroolsContext(){}

    /**
     * 初始化FusionContext: 创建 KieServices / KieContainer
     */
    private static void initContext(){

        System.setProperty("drools.dateformat", "yyyy-MM-dd'T'HH:mm:ss.SSS+0800");

        kieServices = KieServices.Factory.get() ;
        // 获取默认的 kmoudule.xml ： resource/META-INF/kmoudule.xml
        kieContainer = kieServices.getKieClasspathContainer() ;
    }

    /**
     *  获取指定名称的 KieSession 对象
     * @param sessionName
     * @return
     */
    public static KieSession build (String sessionName){

        // 构建规则引擎上下文，即：创建KieServices/KieContainer
        if( kieServices == null || kieContainer == null ){
            initContext() ;
        }
        // 获取Session：session对应 kmoudule.xml中的每个 kbase section 设定的选项
        KieSession kieSession = kieContainer.newKieSession(sessionName) ;

        return kieSession ;
    }

    /**
     *  获取指定名称的 KieSession 对象
     * @param sessionName
     * @return
     */
    public static StatelessKieSession buildStateless (String sessionName){

        // 构建规则引擎上下文，即：创建KieServices/KieContainer
        if( kieServices == null || kieContainer == null ){
            initContext() ;
        }
        // 获取Session：session对应 kmoudule.xml中的每个 kbase section 设定的选项
        StatelessKieSession statelessKieSession = kieContainer.newStatelessKieSession(sessionName);

        return statelessKieSession ;
    }

}
