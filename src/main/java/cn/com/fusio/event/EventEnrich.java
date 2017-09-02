package cn.com.fusio.event;

import cn.com.fusio.datasource.db.DBQueryService;
import cn.com.fusio.event.merge.PinganEduPVEnrich;
import cn.com.fusio.event.entity.ArticleInfo;
import cn.com.fusio.event.entity.FormInfo;
import cn.com.fusio.event.entity.UserInfo;
import cn.com.fusio.event.raw.PinganBehaviorData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author : Ernest
 * @Date : 2017/8/22 14:53
 */
public class EventEnrich {

    private static Logger logger = LogManager.getLogger(EventEnrich.class) ;

    /**
     * PinganBehaviorData行为数据 添加用户信息，内容信息，表单信息
     * @param dataList
     * @return
     */
    public static List<PinganEduPVEnrich> enrichPinganEduPV( final List<PinganBehaviorData> dataList ){

        List<PinganEduPVEnrich> enrichedList = new ArrayList<PinganEduPVEnrich>() ;

        if(dataList != null && !dataList.isEmpty()){

            for(int i = 0 ; i < dataList.size() ;i++){
                PinganEduPVEnrich event = new PinganEduPVEnrich() ;
                PinganBehaviorData behaviorData = dataList.get(i);

                String userid = behaviorData.getUserid();
                String contentId = behaviorData.getArticleId();
                String contentType = behaviorData.getContentType() ;

                // 用户信息
                UserInfo userInfo = DBQueryService.queryUserInfoById(userid);
                //内容信息
                ArticleInfo articleInfo = null ;
                FormInfo formInfo = null ;

                if("article".equals(contentType)){
                    articleInfo = DBQueryService.queryArticleInfoById(contentId);
                    // DRL文件需要的 Enriched Event
                    event = new PinganEduPVEnrich(behaviorData, userInfo, articleInfo);
                } else if("form".equals(contentType)){
                    formInfo = DBQueryService.queryFormInfoById(contentId) ;
                    // DRL文件需要的 Enriched Event
                    event = new PinganEduPVEnrich(behaviorData, userInfo, formInfo);
                }

                enrichedList.add(event);
            }
        }

        return enrichedList ;
    }

}
