package com.zdsn.mq.config;

/**
 * MQ配置
 */
public class Config {

    //MQ端口号
    public static final int SERVICE_PORT = 9999;

    //发布消息
    public final static String PUBLISH = "PUBLISH";
    //订阅
    public final static String SUBSCRIBE = "SUBSCRIBE";
    //获取消息
    public final static String GET = "GET";
    //全广播获取消息
    public final static String RECEIVE = "RECEIVE";

    //生产消息
    public final static String PRODUCE = "PRODUCE";
    //注册
    public final static String REGISTER = "REGISTER";
    //退出
    public final static String UNREGISTER = "UNREGISTER";
    //消费信息
    public final static String CONSUME = "CONSUME";

}
