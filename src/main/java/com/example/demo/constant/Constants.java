/*
 * 文件名：Constants.java
 * 描述：项目常见常量。
 * 修改人：刘可
 * 修改时间：2021-03-09
 */

package com.example.demo.constant;

/**
 * 常见常量类。
 * 
 * @author 刘可
 * @version 1.0.0.0
 * @since 2021-03-09
 */
public abstract class Constants
{
    public static final String TOKEN = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final int NUM_TOKENPREFIXLENGTH = TOKEN_PREFIX.length();
    public static final String SECURITY_USER = "user";
    public static final String SECURITY_ADMIN = "administrator";
    public static final long TIME_1MIN = 60000;
    public static final long TIME_1HOUR = 360000;
    public static final int NUM_16 = 16;
    public static final int NUM_32 = 32;
    public static final int NUM_36 = 36;
    public static final String REGEXP_PHONE = "\\d{15}";
    public static final String REGEXP_ID = "\\d+";
    public static final String CHARASET_UTF8 = "utf-8";
    public static final String CHARASET_UTF8CONTENTYPE =
            "application/json;charset=utf-8";
    public static final String SESSION_SMS = "sms";
    public static final String SESSION_SIGNIN = "signin";
    public static final String SESSION_ARTICLE = "article";
    public static final String SESSION_LOCATION = "loc";
    public static final String KEY_SMS_TIME = "t";
    public static final String KEY_SMS_CODE = "code";
    public static final String KEY_SMS_JS = "js";
    public static final String KEY_SMS_SECRET = "server";
    public static final String KEY_USER_PHONE = "phone";
    public static final String KEY_USER_ID = "uid";
    public static final String KEY_USER_ACCOUNT = "account";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_ICON = "usericon";
    public static final String KEY_USER_SIGNATURE = "signature";
    public static final String KEY_USER_POINT = "point";
    public static final String KEY_USER_PASSWORD = "pwd";
    public static final String KEY_USER_INTRODUCTION = "intro";
    public static final String KEY_USER_BIRTHDAY = "birthday";
    public static final String KEY_USER_COLLEGE = "college";
    public static final String KEY_USER_ADDRESS = "addr";
    public static final String KEY_USER_COMPANY = "com";
    public static final String KEY_USER_SEX = "sex";
    public static final String KEY_USER_LOCK = "ulock";
    public static final String KEY_USER_BAN = "uban";
    public static final String KEY_ARTICLE_ID = "aid";
    public static final String KEY_ARTICLE_AUTHOR = "author";
    public static final String KEY_ARTICLE_CREATETIME = "atime";
    public static final String KEY_ARTICLE_TITLE = "title";
    public static final String KEY_ARTICLE_DEL = "adel";
    public static final String KEY_ARTICLE_DRAFT = "draft";
    public static final String KEY_ARTICLE_TYPE = "at";
    public static final String KEY_ARTICLE_MODIFYTIME = "amodify";
    public static final String KEY_ARTICLE_TEXT = "atext";
    public static final String KEY_ARTICLE_SOURCE = "asource";
    public static final String KEY_COMMENT_ID = "cid";
    public static final String KEY_COMMENT_ARTICLE = "ca";
    public static final String KEY_COMMENT_AUTHOR = "cuser";
    public static final String KEY_COMMENT_CREATETIME = "ctime";
    public static final String KEY_COMMENT_TEXT = "ctext";
    public static final String KEY_COMMENT_DEL = "cdel";
    public static final String KEY_FAVORITE_USER = "favuser";
    public static final String KEY_FAVORITE_ARTICLE = "favarticle";
    public static final String KEY_FAVORITE_CREATETIME = "favtime";
    public static final String KEY_FOCUS_USER = "focus";
    public static final String KEY_FOCUS_FAN = "fan";
    public static final String KEY_FOCUS_CREATETIME = "focustime";
    public static final String KEY_LIKE_USER = "likeer";
    public static final String KEY_LIKE_ARTICLE = "like";
    public static final String KEY_LIKE_DATE = "liketime";
    public static final String KEY_MSG_USER = "addressee";
    public static final String KEY_MSG_SENDER = "sender";
    public static final String KEY_MSG_DATETIME = "msgdate";
    public static final String KEY_MSG_TEXT = "msg";
    public static final String KEY_MSG_DEL = "msgdel";
    public static final String KEY_MSG_HIDE = "msghide";
    public static final String KEY_REPORT_ID = "rid";
    public static final String KEY_REPORT_ARTICLE = "rarticle";
    public static final String KEY_REPORT_USER = "ruser";
    public static final String KEY_REPORT_CREATETIME = "rtime";
    public static final String KEY_REPORT_CAUSE = "rc";
    public static final String KEY_REPORT_DEL = "rdel";
    public static final String KEY_SIGNIN_METHOD = "signinmethod";
    public static final String KEY_SIGNIN_IPV6 = "ipv6[]";
    public static final String KEY_SIGNIN_IPV4 = "ipv4";
    public static final String KEY_SIGNIN_MAC = "mac[]";
    public static final String KEY_SIGNIN_GPS = "gps";
    public static final String KEY_TOPIC_ID = "tid";
    public static final String KEY_TOPIC_MASTER = "tm";
    public static final String KEY_TOPIC_CREATETIME = "ttime";
    public static final String KEY_TOPIC_NAME = "tname";
    public static final String KEY_TOPIC_DEL = "tdel";
    public static final String KEY_TOPIC_BAN = "tban";
    public static final String KEY_TOPIC_MAXSIZE = "tmax";
    public static final String KEY_TOPIC_SIZE = "tsize";
    public static final String KEY_TOPIC_INTRODUCTION = "tintro";
    public static final String KEY_TOPIC_USER = "tuser";
    public static final String KEY_TOPIC_JOINTIME = "tjoin";
    public static final String KEY_TOPIC_USERDEL = "tuserdel";
    public static final String KEY_VISITOR = "visitor";
    public static final String KEY_VISIT_ARTICLE = "varticle";
    public static final String KEY_VISIT_TIME = "vtime";
    public static final String KEY_VISIT_IPV6 = "vipv6";
    public static final String KEY_VISIT_IPV4 = "vipv4";
    public static final String KEY_VISIT_MAC = "vmac";
    public static final String ETC_UNITMINUTE = "分钟";
    public static final String ETC_PROJECTNAME = "星云社区";
}
