package cn.com.fusio.event.entity;

/**
 * @Description: 用户信息表
 * @Author : Ernest
 * @Date : 2017/8/22 9:57
 */
public class UserInfo {

    /**
     * 用户ID
     */
    private Long userid ;
    /**
     * 真实年龄
     */
    private Integer real_age ;
    /**
     * 年龄
     */
    private Integer age ;
    /**
     * 性别
     */
    private String sex ;
    /**
     * 邮件地址
     */
    private String email ;
    /**
     * 省份全称
     */
    private String all_province ;
    /**
     * 用户所属组名称
     */
    private String userExpGroup ;
    /**
     * 用户所属组ID
     */
    private Integer userExpGroupid ;
    /**
     * 活动ID
     */
    private Integer activityid ;
    private Integer is_email_unsubs ;
    private Integer addtime_hour ;
    private Integer addtime_week ;
    private String carloan ;
    /**
     * 屏幕大小
     */
    private String clear ;
    private String client ;
    private String ipcity ;
    private String ipprovince ;
    private String ispc ;
    private String mobileprovince ;
    private String mobiletype ;
    private String question1 ;
    private String question2 ;
    /**
     * 终端类型
     */
    private String terminal_type ;
    private String userprovince ;
    private String view ;

    public UserInfo(){}

    public UserInfo(Long userid, Integer real_age, Integer age, String sex, String email, String all_province, String userExpGroup, Integer userExpGroupid, Integer activityid, Integer is_email_unsubs, Integer addtime_hour, Integer addtime_week, String carloan, String clear, String client, String ipcity, String ipprovince, String ispc, String mobileprovince, String mobiletype, String question1, String question2, String terminal_type, String userprovince, String view) {
        this.userid = userid;
        this.real_age = real_age;
        this.age = age;
        this.sex = sex;
        this.email = email;
        this.all_province = all_province;
        this.userExpGroup = userExpGroup;
        this.userExpGroupid = userExpGroupid;
        this.activityid = activityid;
        this.is_email_unsubs = is_email_unsubs;
        this.addtime_hour = addtime_hour;
        this.addtime_week = addtime_week;
        this.carloan = carloan;
        this.clear = clear;
        this.client = client;
        this.ipcity = ipcity;
        this.ipprovince = ipprovince;
        this.ispc = ispc;
        this.mobileprovince = mobileprovince;
        this.mobiletype = mobiletype;
        this.question1 = question1;
        this.question2 = question2;
        this.terminal_type = terminal_type;
        this.userprovince = userprovince;
        this.view = view;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Integer getReal_age() {
        return real_age;
    }

    public void setReal_age(Integer real_age) {
        this.real_age = real_age;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAll_province() {
        return all_province;
    }

    public void setAll_province(String all_province) {
        this.all_province = all_province;
    }

    public String getUserExpGroup() {
        return userExpGroup;
    }

    public void setUserExpGroup(String userExpGroup) {
        this.userExpGroup = userExpGroup;
    }

    public Integer getUserExpGroupid() {
        return userExpGroupid;
    }

    public void setUserExpGroupid(Integer userExpGroupid) {
        this.userExpGroupid = userExpGroupid;
    }

    public Integer getActivityid() {
        return activityid;
    }

    public void setActivityid(Integer activityid) {
        this.activityid = activityid;
    }

    public Integer getIs_email_unsubs() {
        return is_email_unsubs;
    }

    public void setIs_email_unsubs(Integer is_email_unsubs) {
        this.is_email_unsubs = is_email_unsubs;
    }

    public Integer getAddtime_hour() {
        return addtime_hour;
    }

    public void setAddtime_hour(Integer addtime_hour) {
        this.addtime_hour = addtime_hour;
    }

    public Integer getAddtime_week() {
        return addtime_week;
    }

    public void setAddtime_week(Integer addtime_week) {
        this.addtime_week = addtime_week;
    }

    public String getCarloan() {
        return carloan;
    }

    public void setCarloan(String carloan) {
        this.carloan = carloan;
    }

    public String getClear() {
        return clear;
    }

    public void setClear(String clear) {
        this.clear = clear;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getIpcity() {
        return ipcity;
    }

    public void setIpcity(String ipcity) {
        this.ipcity = ipcity;
    }

    public String getIpprovince() {
        return ipprovince;
    }

    public void setIpprovince(String ipprovince) {
        this.ipprovince = ipprovince;
    }

    public String getIspc() {
        return ispc;
    }

    public void setIspc(String ispc) {
        this.ispc = ispc;
    }

    public String getMobileprovince() {
        return mobileprovince;
    }

    public void setMobileprovince(String mobileprovince) {
        this.mobileprovince = mobileprovince;
    }

    public String getMobiletype() {
        return mobiletype;
    }

    public void setMobiletype(String mobiletype) {
        this.mobiletype = mobiletype;
    }

    public String getQuestion1() {
        return question1;
    }

    public void setQuestion1(String question1) {
        this.question1 = question1;
    }

    public String getQuestion2() {
        return question2;
    }

    public void setQuestion2(String question2) {
        this.question2 = question2;
    }

    public String getTerminal_type() {
        return terminal_type;
    }

    public void setTerminal_type(String terminal_type) {
        this.terminal_type = terminal_type;
    }

    public String getUserprovince() {
        return userprovince;
    }

    public void setUserprovince(String userprovince) {
        this.userprovince = userprovince;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userid=" + userid +
                ", real_age=" + real_age +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", userExpGroup='" + userExpGroup + '\'' +
                ", userExpGroupid=" + userExpGroupid +
                ", activityid=" + activityid +
                '}';
    }
}