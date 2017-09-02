package cn.com.fusio.event.entity;

import java.util.Date;

/**
 * @Description: 内容信息表
 * @Author : Ernest
 * @Date : 2017/8/22 9:58
 */
public class ArticleInfo {

    private String article_id ;
    private String article_title ;
    private String article_category_id ;
    private String catg_name ;
    private Date article_date ;
    private Integer status ;
    private String send_channel ;
    private String issue_channel ;
    private String revoke_channel ;
    private Date create_time ;
    private Date last_mod_time ;
    private String author ;
    private String cover_pic ;
    private String tags ;
    private String introduction ;
    private String text_body ;
    private Integer is_enable_intro ;
    private Integer likes_to_display ;
    private Integer max_list_display ;
    private Integer max_detail_display ;
    private String admin_id ;
    private String article_url ;
    private Integer article_type ;
    private String appmsg_id ;
    private String pic_author ;
    private String cover_file_path ;
    private Integer is_selected ;
    private Integer is_hot ;
    private String monthly ;

    public ArticleInfo() {}

    public ArticleInfo(String article_id, String article_title, String article_category_id, String catg_name, Date article_date, Integer status, String send_channel, String issue_channel, String revoke_channel, Date create_time, Date last_mod_time, String author, String cover_pic, String tags, String introduction, String text_body, Integer is_enable_intro, Integer likes_to_display, Integer max_list_display, Integer max_detail_display, String admin_id, String article_url, Integer article_type, String appmsg_id, String pic_author, String cover_file_path, Integer is_selected, Integer is_hot, String monthly) {
        this.article_id = article_id;
        this.article_title = article_title;
        this.article_category_id = article_category_id;
        this.catg_name = catg_name;
        this.article_date = article_date;
        this.status = status;
        this.send_channel = send_channel;
        this.issue_channel = issue_channel;
        this.revoke_channel = revoke_channel;
        this.create_time = create_time;
        this.last_mod_time = last_mod_time;
        this.author = author;
        this.cover_pic = cover_pic;
        this.tags = tags;
        this.introduction = introduction;
        this.text_body = text_body;
        this.is_enable_intro = is_enable_intro;
        this.likes_to_display = likes_to_display;
        this.max_list_display = max_list_display;
        this.max_detail_display = max_detail_display;
        this.admin_id = admin_id;
        this.article_url = article_url;
        this.article_type = article_type;
        this.appmsg_id = appmsg_id;
        this.pic_author = pic_author;
        this.cover_file_path = cover_file_path;
        this.is_selected = is_selected;
        this.is_hot = is_hot;
        this.monthly = monthly;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_category_id() {
        return article_category_id;
    }

    public void setArticle_category_id(String article_category_id) {
        this.article_category_id = article_category_id;
    }

    public String getCatg_name() {
        return catg_name;
    }

    public void setCatg_name(String catg_name) {
        this.catg_name = catg_name;
    }

    public Date getArticle_date() {
        return article_date;
    }

    public void setArticle_date(Date article_date) {
        this.article_date = article_date;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSend_channel() {
        return send_channel;
    }

    public void setSend_channel(String send_channel) {
        this.send_channel = send_channel;
    }

    public String getIssue_channel() {
        return issue_channel;
    }

    public void setIssue_channel(String issue_channel) {
        this.issue_channel = issue_channel;
    }

    public String getRevoke_channel() {
        return revoke_channel;
    }

    public void setRevoke_channel(String revoke_channel) {
        this.revoke_channel = revoke_channel;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getLast_mod_time() {
        return last_mod_time;
    }

    public void setLast_mod_time(Date last_mod_time) {
        this.last_mod_time = last_mod_time;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getText_body() {
        return text_body;
    }

    public void setText_body(String text_body) {
        this.text_body = text_body;
    }

    public Integer getIs_enable_intro() {
        return is_enable_intro;
    }

    public void setIs_enable_intro(Integer is_enable_intro) {
        this.is_enable_intro = is_enable_intro;
    }

    public Integer getLikes_to_display() {
        return likes_to_display;
    }

    public void setLikes_to_display(Integer likes_to_display) {
        this.likes_to_display = likes_to_display;
    }

    public Integer getMax_list_display() {
        return max_list_display;
    }

    public void setMax_list_display(Integer max_list_display) {
        this.max_list_display = max_list_display;
    }

    public Integer getMax_detail_display() {
        return max_detail_display;
    }

    public void setMax_detail_display(Integer max_detail_display) {
        this.max_detail_display = max_detail_display;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getArticle_url() {
        return article_url;
    }

    public void setArticle_url(String article_url) {
        this.article_url = article_url;
    }

    public Integer getArticle_type() {
        return article_type;
    }

    public void setArticle_type(Integer article_type) {
        this.article_type = article_type;
    }

    public String getAppmsg_id() {
        return appmsg_id;
    }

    public void setAppmsg_id(String appmsg_id) {
        this.appmsg_id = appmsg_id;
    }

    public String getPic_author() {
        return pic_author;
    }

    public void setPic_author(String pic_author) {
        this.pic_author = pic_author;
    }

    public String getCover_file_path() {
        return cover_file_path;
    }

    public void setCover_file_path(String cover_file_path) {
        this.cover_file_path = cover_file_path;
    }

    public Integer getIs_selected() {
        return is_selected;
    }

    public void setIs_selected(Integer is_selected) {
        this.is_selected = is_selected;
    }

    public Integer getIs_hot() {
        return is_hot;
    }

    public void setIs_hot(Integer is_hot) {
        this.is_hot = is_hot;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }

    @Override
    public String toString() {
        return "ArticleInfo{" +
                "article_id='" + article_id + '\'' +
                ", article_title='" + article_title + '\'' +
                ", article_category_id='" + article_category_id + '\'' +
                ", catg_name='" + catg_name + '\'' +
                ", article_date=" + article_date +
                ", status=" + status +
                ", send_channel='" + send_channel + '\'' +
                ", issue_channel='" + issue_channel + '\'' +
                ", revoke_channel='" + revoke_channel + '\'' +
                ", create_time=" + create_time +
                ", last_mod_time=" + last_mod_time +
                ", author='" + author + '\'' +
                ", cover_pic='" + cover_pic + '\'' +
                ", tags='" + tags + '\'' +
                ", introduction='" + introduction + '\'' +
                ", text_body='" + text_body + '\'' +
                ", is_enable_intro=" + is_enable_intro +
                ", likes_to_display=" + likes_to_display +
                ", max_list_display=" + max_list_display +
                ", max_detail_display=" + max_detail_display +
                ", admin_id='" + admin_id + '\'' +
                ", article_url='" + article_url + '\'' +
                ", article_type=" + article_type +
                ", appmsg_id='" + appmsg_id + '\'' +
                ", pic_author='" + pic_author + '\'' +
                ", cover_file_path='" + cover_file_path + '\'' +
                ", is_selected=" + is_selected +
                ", is_hot=" + is_hot +
                ", monthly='" + monthly + '\'' +
                '}';
    }
}
