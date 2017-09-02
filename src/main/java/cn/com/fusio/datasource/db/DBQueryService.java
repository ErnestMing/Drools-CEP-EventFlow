package cn.com.fusio.datasource.db;

import cn.com.fusio.cache.RedisConstant;
import cn.com.fusio.datasource.db.core.DBUtils;
import cn.com.fusio.event.entity.ArticleInfo;
import cn.com.fusio.event.entity.FormInfo;
import cn.com.fusio.event.entity.UserInfo;
import cn.com.fusio.utils.RedisUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description: 数据查询
 * @Author : Ernest
 * @Date : 2017/8/22 10:36
 */
public class DBQueryService {

    private static Logger logger = LogManager.getLogger(DBQueryService.class) ;

    /**
     * 用户表userid：16 ~ 5554917
     */
    private static String user_data_pingan_email = "user_data_pingan_email" ;
    /**
     * 用户表userid：20000001 ~ 25000000
     */
    private static String user_data_notpingan = "user_data_notpingan" ;

    /**
     * 用户表查询SQL模板
     */
    private static String user_sql = "SELECT * FROM fusio_ai.%s WHERE userid = %s " ;
    /**
     * 表单查询SQl模板
     */
    private static String form_sql = "SELECT * FROM edu_pingan_fm.bd_form WHERE form_id = %s " ;
    /**
     * 内容查询SQl模板
     */
    private static String article_sql = "SELECT * FROM edu_pingan_fm.nm_article WHERE article_id = '%s' " ;


    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    public static UserInfo queryUserInfoById(String userId ){

        UserInfo userInfo = null ;

        Long id = Long.parseLong(userId) ;

        // 1.先从Redis缓存获取数据
        if(RedisUtil.isExistInHash(RedisConstant.user_key,userId)){
            logger.info(RedisConstant.user_key+":"+userId+" is exist in redis.");
            userInfo = RedisUtil.getObjFromHash(RedisConstant.user_key,userId,UserInfo.class) ;
            return userInfo ;
        }

        // 2.从 mysql 数据库获取数据
        String sql = "" ;
        if(id >= 16 && id <= 5554917){
            sql = String.format(user_sql, user_data_pingan_email,userId) ;

        } else if(id >= 20000001 && id <= 25000000){
            sql = String.format(user_sql, user_data_notpingan,userId) ;
        }else{
            logger.debug("----> userId 不在对应的区间, sql:"+sql);
            return null ;
        }

        logger.debug("----> query sql:"+sql);
        ResultSet rs = null ;
        try {
            rs = DBUtils.queryRowBySql(sql);
            userInfo = parseToUserInfo(rs);
            // 3.缓存数据到 Redis
            logger.info("redis: cahce UserInfo to "+RedisConstant.user_key+",fieldName="+userId);
            RedisUtil.saveObj2Hash(RedisConstant.user_key,userId,userInfo);

        } catch (SQLException e) {
            logger.error("---->Exception: sql="+sql+"    "+e.getMessage());
        }

        return userInfo  ;
    }

    /**
     * 查询文章信息
     * @param articleId
     * @return
     */
    public static ArticleInfo queryArticleInfoById(String articleId ){

        ArticleInfo articleInfo = null ;

        // 1.先从Redis缓存获取数据
        if(RedisUtil.isExistInHash(RedisConstant.article_key,articleId)){
            logger.info(RedisConstant.article_key+":"+articleId+" is exist in redis.");
            articleInfo = RedisUtil.getObjFromHash(RedisConstant.article_key,articleId,ArticleInfo.class) ;
            return articleInfo ;
        }

        // 2.从 mysql 数据库获取数据
        String sql = String.format(article_sql,articleId) ;

        logger.debug("----> query sql:"+sql);
        ResultSet rs = null ;
        try {
            rs = DBUtils.queryRowBySql(sql);
            articleInfo = parseToContentInfo(rs);
            // 3.缓存数据到 Redis
            logger.info("redis: cahce ArticleInfo to "+RedisConstant.article_key+",fieldName="+articleId);
            RedisUtil.saveObj2Hash(RedisConstant.article_key,articleId,articleInfo);

        } catch (SQLException e) {
            logger.error("---->Exception: sql="+sql+"    "+e.getMessage());
        }

        return articleInfo  ;
    }

    /**
     * 查询表单信息
     * @param formId
     * @return
     */
    public static FormInfo queryFormInfoById( String formId ){
        FormInfo formInfo = null ;

        // 1.先从Redis缓存获取数据
        if(RedisUtil.isExistInHash(RedisConstant.form_key,formId)){
            logger.info(RedisConstant.form_key+":"+formId+" is exist in redis.");
            formInfo = RedisUtil.getObjFromHash(RedisConstant.form_key,formId,FormInfo.class) ;
            return formInfo ;
        }

        // 2.从 mysql 数据库获取数据
        String sql = String.format(form_sql,formId) ;

        logger.debug("----> query sql:"+sql);
        ResultSet rs = null ;
        try {
            rs = DBUtils.queryRowBySql(sql);
            formInfo= parseToFormInfo(rs);
            // 3.缓存数据到 Redis
            logger.info("redis: cahce FormInfo to "+RedisConstant.form_key+",fieldName="+formId);
            RedisUtil.saveObj2Hash(RedisConstant.form_key,formId,formInfo);

        } catch (SQLException e) {
            logger.error("---->Exception: sql="+sql+"    "+e.getMessage());
        }

        return formInfo  ;
    }

    /**
     * 解析到 用户 Entity
     * @param rs
     * @return
     * @throws SQLException
     */
    private static UserInfo parseToUserInfo(ResultSet rs) throws SQLException {
        UserInfo userInfo = null ;
        if (rs.first()){
            userInfo = new UserInfo(
                rs.getLong("userid") ,
                rs.getInt("real_age") ,
                rs.getInt("age") ,
                rs.getString("sex") ,
                rs.getString("email") ,
                rs.getString("all_province") ,
                rs.getString("userExpGroup") ,
                rs.getInt("userExpGroupid") ,
                rs.getInt("activityid") ,
                rs.getInt("is_email_unsubs") ,
                rs.getInt("addtime_hour") ,
                rs.getInt("addtime_week") ,
                rs.getString("carloan") ,
                rs.getString("clear") ,
                rs.getString("client") ,
                rs.getString("ipcity") ,
                rs.getString("ipprovince") ,
                rs.getString("ispc") ,
                rs.getString("mobileprovince") ,
                rs.getString("mobiletype") ,
                rs.getString("question1") ,
                rs.getString("question2") ,
                rs.getString("terminal_type") ,
                rs.getString("userprovince") ,
                rs.getString("view")
            ) ;
        }
        return userInfo ;
    }

    /**
     * 解析到 表单 Entity
     * @param rs
     * @return
     * @throws SQLException
     */
    public static FormInfo parseToFormInfo( ResultSet rs ) throws SQLException {
        FormInfo formInfo = null ;

        if(rs.first()){
            formInfo = new FormInfo(
                rs.getInt("form_id") ,
                rs.getString("form_title") ,
                rs.getString("form_desc") ,
                rs.getString("cover_pic") ,
                rs.getInt("page_progress") ,
                rs.getInt("fill_limit") ,
                rs.getInt("form_type") ,
                rs.getString("text_body") ,
                rs.getString("global_backg_pic") ,
                rs.getString("admin_id") ,
                rs.getDate("create_time") ,
                rs.getInt("status") ,
                rs.getDate("issue_time") ,
                rs.getDate("last_mod_time") ,
                rs.getString("form_tags") ,
                rs.getInt("is_fb_right")
            ) ;
        }
        return formInfo ;
    }

    /**
     * 解析到 内容 Entity
     * @param rs
     * @return
     * @throws SQLException
     */
    public static ArticleInfo parseToContentInfo(ResultSet rs ) throws SQLException {

        ArticleInfo contentInfo = null ;
        if(rs.first()){
            contentInfo = new ArticleInfo(
                    rs.getString("article_id"),
                    rs.getString("article_title"),
                    rs.getString("article_category_id"),
                    rs.getString("catg_name"),
                    rs.getDate("article_date"),
                    rs.getInt("status"),
                    rs.getString("send_channel"),
                    rs.getString("issue_channel"),
                    rs.getString("revoke_channel"),
                    rs.getDate("create_time"),
                    rs.getDate("last_mod_time"),
                    rs.getString("author"),
                    rs.getString("cover_pic"),
                    rs.getString("tags"),
                    rs.getString("introduction"),
                    rs.getString("text_body"),
                    rs.getInt("is_enable_intro"),
                    rs.getInt("likes_to_display"),
                    rs.getInt("max_list_display"),
                    rs.getInt("max_detail_display"),
                    rs.getString("admin_id"),
                    rs.getString("article_url"),
                    rs.getInt("article_type"),
                    rs.getString("appmsg_id"),
                    rs.getString("pic_author"),
                    rs.getString("cover_file_path"),
                    rs.getInt("is_selected"),
                    rs.getInt("is_hot"),
                    rs.getString("monthly")
            );
        }
        return contentInfo ;
    }

    public static void main(String[] args) {
        UserInfo userInfo = DBQueryService.queryUserInfoById("24000000");
        FormInfo formInfo = DBQueryService.queryFormInfoById("12");
        ArticleInfo contentInfo = DBQueryService.queryArticleInfoById("20170211081000AR-0TSFMG4EP9GUDQC");


        System.out.println(userInfo);
        System.out.println(formInfo);
        System.out.println(contentInfo);
    }
}
