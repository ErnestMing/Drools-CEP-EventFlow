package cn.com.fusio.drools.actions;

import cn.com.fusio.cache.RedisConstant;
import cn.com.fusio.event.BaseEvent;
import cn.com.fusio.event.merge.PinganEduPVEnrich;
import cn.com.fusio.event.raw.PinganBehaviorData;
import cn.com.fusio.utils.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.drools.core.util.StringUtils;
import org.scalactic.Bool;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 主要DRL文件中调用，数据预处理 与 Action fire
 *      Normal：rule action 封装
 *      CEP   ：主要基于时间推理 + 访问次数等多条件组合时，进行对该时间之内时间的处理
 *      AccFre :访问频率
 *      AccDep :访问深度
 *      TimeRea:时间推理 time reasoning
 * @Author : Ernest
 * @Date : 2017/8/21 9:44
 */
public class RuleActionInvoker {

    private static Logger logger = LogManager.getLogger(RuleActionInvoker.class) ;

    /**
     * 某一段时间范围之内（一周，一月）：
     *      访问次数 > n 或 访问带有xxx标签的用户，触发相应Action
     * @param dataList   数据List
     * @param num     访问频率
     */
    public static void act_when_AccFre_gt_n(String ruleName , List<? extends BaseEvent> dataList , long num){

        logger.info("match:rule=["+ruleName+"]");

        if(dataList != null && !dataList.isEmpty()){

            PinganEduPVEnrich curEvent = (PinganEduPVEnrich)dataList.get(dataList.size() - 1);

            try{
                logger.info("---->userId:"+curEvent.getBehaviorData().getUserid()+"   c_time:"+curEvent.getBehaviorData().getCollector_tstamp());
                logger.info("---->rule:dataList[size="+dataList.size()+"]");

                String curUserId = curEvent.getBehaviorData().getUserid();

                long recordSize = 0L ;

                List<PinganEduPVEnrich> tmpList = new ArrayList<PinganEduPVEnrich>() ;

                if(curUserId != null && !curUserId.isEmpty()){
                    PinganEduPVEnrich behaviorData = null ;

                    for( int i = 0 ; i< dataList.size() ;i++){

                            behaviorData = (PinganEduPVEnrich)dataList.get(i) ;

//                            logger.info("---->"+behaviorData.getBehaviorData().getUserid()+"    "+behaviorData.getEventTime());
                            String userid = behaviorData.getBehaviorData().getUserid();

                            if(userid != null && userid.equals(curUserId)){
    //                        logger.info("---->++"+behaviorData.getBehaviorData().getUserid());
                                recordSize++ ;
                                tmpList.add(behaviorData);
                            }
                    }
                }
                /**
                 * 由于 CEP 是流式的，所以 frequence > n 时：需要 recodeSize = n ;
                 * 即：当 = n 时，触发，防止后面有重复记录
                 */
                if(recordSize == num){
                    if(!RedisUtil.isExistInSet(RedisConstant.sent_lt_1_day_key,curUserId)){

                        logger.info("----> action=act_when_AccFre_gt_n: sendEmail to curUserId="+curUserId +"  accessTime="+recordSize);
                        //缓存 用户ID ,1天之内触发过的用户
                        RedisUtil.saveObj2Set(RedisConstant.sent_lt_1_day_key,curUserId);
                    }
                }
                logger.info("---------------------------------------------------------------------------------------");
            }catch (Exception e){
                logger.info("----->Exception:"+e.getMessage()+" ["+curEvent.toString()+"]");
            }
        }
    }

    // 完成了某个表单的测验：URL

    /**
     * 打印事件
     */
    public static void print_event(List<? extends BaseEvent> dataList){

//        PinganEduPVEnrich curEvent = (PinganEduPVEnrich)triggerEvent ;
//        PinganEduPVEnrich curEvent = (PinganEduPVEnrich)dataList.get(0) ;
//        PinganEduPVEnrich curEvent2 = (PinganEduPVEnrich)dataList.get(dataList.size()-1) ;
//
//        System.out.println("drl:event:triggered --> "+curEvent.toString());
//        System.out.println("drl:event:triggered --> "+curEvent2.toString());

        for(int i=0 ;i<dataList.size();i++){
            System.out.println("drl:event:triggered --> "+dataList.get(i).toString());
        }
    }



    // action list
    // action01:
    // action02:
    // action03:

}
