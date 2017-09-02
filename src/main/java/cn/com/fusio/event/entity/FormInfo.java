package cn.com.fusio.event.entity;

import java.util.Date;

/**
 * @Description: 表单信息表
 * @Author : Ernest
 * @Date : 2017/8/22 9:58
 */
public class FormInfo {

    private Integer form_id ;
    private String form_title ;
    private String form_desc ;
    private String cover_pic ;
    private Integer page_progress ;
    private Integer fill_limit ;
    private Integer form_type ;
    private String text_body ;
    private String global_backg_pic ;
    private String admin_id ;
    private Date create_time ;
    private Integer status ;
    private Date issue_time ;
    private Date last_mod_time ;
    private String form_tags ;
    private Integer is_fb_right ;

    public FormInfo() {}

    public FormInfo(Integer form_id, String form_title, String form_desc, String cover_pic, Integer page_progress, Integer fill_limit, Integer form_type, String text_body, String global_backg_pic, String admin_id, Date create_time, Integer status, Date issue_time, Date last_mod_time, String form_tags, Integer is_fb_right) {
        this.form_id = form_id;
        this.form_title = form_title;
        this.form_desc = form_desc;
        this.cover_pic = cover_pic;
        this.page_progress = page_progress;
        this.fill_limit = fill_limit;
        this.form_type = form_type;
        this.text_body = text_body;
        this.global_backg_pic = global_backg_pic;
        this.admin_id = admin_id;
        this.create_time = create_time;
        this.status = status;
        this.issue_time = issue_time;
        this.last_mod_time = last_mod_time;
        this.form_tags = form_tags;
        this.is_fb_right = is_fb_right;
    }

    public Integer getForm_id() {
        return form_id;
    }

    public void setForm_id(Integer form_id) {
        this.form_id = form_id;
    }

    public String getForm_title() {
        return form_title;
    }

    public void setForm_title(String form_title) {
        this.form_title = form_title;
    }

    public String getForm_desc() {
        return form_desc;
    }

    public void setForm_desc(String form_desc) {
        this.form_desc = form_desc;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public Integer getPage_progress() {
        return page_progress;
    }

    public void setPage_progress(Integer page_progress) {
        this.page_progress = page_progress;
    }

    public Integer getFill_limit() {
        return fill_limit;
    }

    public void setFill_limit(Integer fill_limit) {
        this.fill_limit = fill_limit;
    }

    public Integer getForm_type() {
        return form_type;
    }

    public void setForm_type(Integer form_type) {
        this.form_type = form_type;
    }

    public String getText_body() {
        return text_body;
    }

    public void setText_body(String text_body) {
        this.text_body = text_body;
    }

    public String getGlobal_backg_pic() {
        return global_backg_pic;
    }

    public void setGlobal_backg_pic(String global_backg_pic) {
        this.global_backg_pic = global_backg_pic;
    }

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getIssue_time() {
        return issue_time;
    }

    public void setIssue_time(Date issue_time) {
        this.issue_time = issue_time;
    }

    public Date getLast_mod_time() {
        return last_mod_time;
    }

    public void setLast_mod_time(Date last_mod_time) {
        this.last_mod_time = last_mod_time;
    }

    public String getForm_tags() {
        return form_tags;
    }

    public void setForm_tags(String form_tags) {
        this.form_tags = form_tags;
    }

    public Integer getIs_fb_right() {
        return is_fb_right;
    }

    public void setIs_fb_right(Integer is_fb_right) {
        this.is_fb_right = is_fb_right;
    }

    @Override
    public String toString() {
        return "FormInfo{" +
                "form_id=" + form_id +
                ", form_title='" + form_title + '\'' +
                ", form_desc='" + form_desc + '\'' +
                ", cover_pic='" + cover_pic + '\'' +
                ", page_progress=" + page_progress +
                ", fill_limit=" + fill_limit +
                ", form_type=" + form_type +
                ", text_body='" + text_body + '\'' +
                ", global_backg_pic='" + global_backg_pic + '\'' +
                ", admin_id='" + admin_id + '\'' +
                ", create_time=" + create_time +
                ", status=" + status +
                ", issue_time=" + issue_time +
                ", last_mod_time=" + last_mod_time +
                ", form_tags='" + form_tags + '\'' +
                ", is_fb_right=" + is_fb_right +
                '}';
    }
}
