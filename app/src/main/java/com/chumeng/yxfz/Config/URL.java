package com.chumeng.yxfz.Config;

/**
 * Created by qiu on 2018/8/27.
 */

public class URL {
    private static final String HOST="http://www.chumeng.vip";
    //发送验证码
    public static final String UrlRegSendCode=HOST+"/Api//Mes/regSendCode";
    //注册
    public static final String UrlSave_user_info=HOST+"/Api/Login/save_user_info";
    //登录
    public static final String UrlLoginAction=HOST+"/Api/Login/loginAction";
    //广告列表
    public static final String UrlAdverts=HOST+"/Api/Index/getAdverts";
    //获取服务用户数和接单数
    public static final String UrlGetOrder=HOST+"/Api/Index/getOrder";
    //首页订单列表
    public static final  String UrlOrderList=HOST+"/Api/Index/orderList";
    //上传文件
    public static final String UrlUpload=HOST+"/Home/Api/upload";
    //下单
    public static final String UrlDoCreate=HOST+"/Api/Order/doCreate";
    //获取游戏区服
    public static final String UrlGetGameArea=HOST+"/Api/Index/getGameArea";
    //获取游戏开始段位
    public static final String UrlGetGameStart=HOST+"/Api/Index/getGameStart";
    //获取游戏结束段位
    public static final String UrlGetGameEnd=HOST+"/Api/Index/getGameEnd";
}
