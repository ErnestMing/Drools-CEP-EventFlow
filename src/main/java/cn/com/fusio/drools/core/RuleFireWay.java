package cn.com.fusio.drools.core;

import org.kie.api.runtime.KieSession;

/**
 * 规则触发策略
 * created by zyming in 2017/8/16
 */
public class RuleFireWay {

    /**
     * 该线程会一直运行，激活对应 KieSession 中的 rule
     *      比较消耗内存
     * @param kieSession
     * @param agendaGroup
     */
    public static void alwaysFireRule(KieSession kieSession , String agendaGroup){
        new Thread(new Runnable() {
            @Override
            public void run() {

                while(true){
                    // 设置 pingan_behavior_data 获取到焦点 ,必须放在触发之前，否则会有误差
                    // 获取焦点，触发规则，失去焦点
                    kieSession.getAgenda().getAgendaGroup(agendaGroup).setFocus();
                    // 触发规则
                    kieSession.fireAllRules();
                }
            }
        },"AlwaysFireRuleThread").start(); ;
    }

    /**
     * 单次触发规则
     *  当有 Fact insert 时，触发规则
     * @param kieSession
     */
    public static void basicFireRule(KieSession kieSession ,String agendaGroup){

        //TODO 添加 EventListen

        // 获取焦点需要在 fireAllRules 之前，在每个Fact插入 --> 激活里面
        kieSession.getAgenda().getAgendaGroup(agendaGroup).setFocus();
        kieSession.fireAllRules() ;
    }
}
