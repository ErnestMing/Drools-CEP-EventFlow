package cn.com.fusio.event.merge;

import cn.com.fusio.event.BaseEvent;
import cn.com.fusio.event.entity.ArticleInfo;
import cn.com.fusio.event.entity.FormInfo;
import cn.com.fusio.event.entity.UserInfo;
import cn.com.fusio.event.raw.PinganBehaviorData;

/**
 * @Description: 平安教化-表单-PV+PP 事件
 * @Author : Ernest
 * @Date : 2017/8/21 11:35
 */
public class PinganEduFormPVandPPEvent extends BaseEvent {

    /**
     * 用户行为记录
     */
    private PinganBehaviorData behaviorData ;
    /**
     * 用户信息
     */
    private UserInfo userInfo ;
    /**
     * 文章信息,当 bd:contentType=article 时，有值
     */
    private ArticleInfo articleInfo ;

    /**
     * 表单信息,当 bd:contentType=form 时，有值
     */
    private FormInfo formInfo ;

    public PinganEduFormPVandPPEvent() {}

    public PinganEduFormPVandPPEvent(PinganBehaviorData behaviorData, UserInfo userInfo, ArticleInfo articleInfo) {
        this.behaviorData = behaviorData;
        this.userInfo = userInfo;
        this.articleInfo = articleInfo;

        // 指定 DRL 中的 @timestamp
        this.eventTime = behaviorData.getCollector_tstamp() ;
    }

    public PinganBehaviorData getBehaviorData() {
        return behaviorData;
    }

    public void setBehaviorData(PinganBehaviorData behaviorData) {
        this.behaviorData = behaviorData;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public ArticleInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(ArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }

    public FormInfo getFormInfo() {
        return formInfo;
    }

    public void setFormInfo(FormInfo formInfo) {
        this.formInfo = formInfo;
    }

    @Override
    public String toString() {
        return "PinganEduFormPVandPPEvent{" +
                "behaviorData=" + behaviorData +
                ", userInfo=" + userInfo +
                ", articleInfo=" + articleInfo +
                ", formInfo=" + formInfo +
                '}';
    }
}
