package com.youdu.network.http;

/**
 * @author: vision
 * @function: 所有请求相关地址
 * @date: 16/8/12
 */
public class HttpConstants {

    private static final String ROOT_URL = "http://imooc.com/api";

    /**
     * 请求本地产品列表
     */
    public static String PRODUCT_LIST = ROOT_URL + "/fund/search.php";

    /**
     * 本地产品列表更新时间措请求
     */
    public static String PRODUCT_LATESAT_UPDATE = ROOT_URL + "/fund/upsearch.php";

    /**
     * 登陆接口
     */
    public static String LOGIN = ROOT_URL + "/user/login_phone.php";

    /**
     * 检查更新接口
     */
    public static String CHECK_UPDATE = ROOT_URL + "/config/check_update.php";

    /**
     * 首页产品请求接口
     */
    public static String HOME_RECOMMAND = ROOT_URL + "/product/home_recommand.php";

    /**
     * 课程详情接口
     */
    public static String COURSE_DETAIL = ROOT_URL + "/product/course_detail.php";

    /**
     * QQ在线交谈
     */
    public static String ONLINE_SERVICE = "mqqwpa://im/chat?chat_type=crm&uin=800113900&version=1&src_type=web&web_src=http:://www.qianjing.com";

    /**
     * AndFix patch文件下载地址
     */
    public static String AND_FIX_URL = ROOT_URL + "/and_fix/patch.php";

    /**
     * 检查是否有patch文件更新
     */
    public static String UPDATE_PATCH_URL = ROOT_URL + "/and_fix/update.php";

}


