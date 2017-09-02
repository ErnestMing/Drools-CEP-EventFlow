package cn.com.fusio.event.merge;

import cn.com.fusio.event.BaseEvent;
import cn.com.fusio.event.entity.ArticleInfo;
import cn.com.fusio.event.entity.FormInfo;
import cn.com.fusio.event.entity.UserInfo;
import cn.com.fusio.event.raw.PinganBehaviorData;
import org.drools.core.util.StringUtils;

/**
 * @Description: 丰富 PinganBehaviorData 类
 * @Author : Ernest
 * @Date : 2017/8/20 20:07
 */
public class PinganEduPVEnrich extends BaseEvent {

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

    public PinganEduPVEnrich() {}

    public PinganEduPVEnrich(PinganBehaviorData behaviorData, UserInfo userInfo, ArticleInfo articleInfo) {
        this.behaviorData = behaviorData;
        this.userInfo = userInfo;
        this.articleInfo = articleInfo;

        // 指定 DRL 中的 @timestamp
        this.eventTime = behaviorData.getCollector_tstamp() ;
    }

    /**
     * 判断行为数据是否包含某些标签：PinganEduPVEnrich
     * @param tags
     * @return
     */
    public boolean containTags(String... tags){

        boolean isValid = false ;

        // 1.2.过滤标签条件
        // 59effffd48b743bbbf9ff148341b32ee:事业;63dd1d21acfd4452bf72130123cf2f3c:职位
        String cntTags = "" ;

        String cntType = behaviorData.getContentType();

        if("article".equals(cntType)){
            cntTags = articleInfo.getCatg_name() ;
        }else if("form".equals(cntType)){
//            cntTags = formInfo.getForm_tags() ;
            cntTags = "心理" ;            //暂时 表单 标签都作为 心理。
        }

        // 判断是否包含标签, 并集
        if(!StringUtils.isEmpty(cntTags)){
            for(int i = 0 ; i<tags.length ;i++){
                if(cntTags.equals(tags[i])) isValid = true ;
            }
        }
        return isValid ;
    }

    /**
     * 判断用户是否属于某个省份：或
     * @param city
     * @return
     */
    public Boolean isBelongToProvince(String... city){

        boolean yesOrNo = false ;

        String province = null ;
        if(userInfo != null ){
            province = this.userInfo.getAll_province();
        }

        if(province !=null && "".equals(province)){
            for(int i = 0 ; i < city.length ;i++){
                if(province.equals(city[i])){
                    yesOrNo = true ;
                    break;
                }
            }
        }
        return yesOrNo ;
    }

    /**
     * 年龄大于 age
     * @param age
     * @return
     */
    public Boolean isAgeGtNum(Integer age){

        boolean yesOrNo = false ;
        Integer userAge = null ;
        if(userInfo != null ){
            userAge = userInfo.getAge();
        }

        if( userAge != null ){
            if(userInfo.getAge() > age){
                yesOrNo = true ;
            }
        }

        return yesOrNo ;
    }

    /**
     * 年龄小于 age
     * @param age
     * @return
     */
    public Boolean isAgeLtNum(Integer age){

        boolean yesOrNo = false ;
        if(userInfo.getAge() != null ){
            if(userInfo.getAge() < age){
                yesOrNo = true ;
            }
        }

        return yesOrNo ;
    }

    public PinganEduPVEnrich(PinganBehaviorData behaviorData, UserInfo userInfo, FormInfo formInfo) {
        this.behaviorData = behaviorData;
        this.userInfo = userInfo;
        this.formInfo = formInfo;

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
        return "PinganEduPVEnrich{" +
                "behaviorData=" + behaviorData +
                ", userInfo=" + userInfo +
                ", articleInfo=" + articleInfo +
                ", formInfo=" + formInfo +
                '}';
    }
}
