package com.zdsn.mq.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

public class PTP_Broker {
    //消息队列存储最大值
    private final static int MAX_SIZE=3;
    private static final Logger logger = LoggerFactory.getLogger(PTP_Broker.class);
    //保存消息数据的容器
    private static final ArrayBlockingQueue<String> messageQueue=new ArrayBlockingQueue<>(MAX_SIZE);
    // 当前注册的消费者
    private static String registeredConsumer = null;

    //生产消息
    public static void produce(String msg){
        if (messageQueue.offer(msg)) {
            logger.info("消息发送成功，msg："+msg+",暂存队列中的消息数量是:"+messageQueue.size());
        }else{
            logger.info("消息处理数据中心数据达到最大负荷，不能继续放入消息");
        }
    }

    //消费消息
    public static String consume(String name){
        String msg = null;
        if (registeredConsumer == null){
            return null;
        }
        if (registeredConsumer.equals(name)) {
            msg = messageQueue.poll();
            if (msg != null) {
                // 消费条件满足时从消息容器中取出一条消息
                logger.info("已经消费消息：" + msg + ",消息队列中暂存的消息数为" + messageQueue.size());
            } else {
                logger.info("消息处理中心内没有消息可供消费！");
            }
        } else {
            logger.info("当前有其他消费者正在使用消息队列，无法消费消息");
        }
        return msg;
    }

    // 注册消费者
    public static void registerConsumer(String name) {
        if (isConsumerRegistered(name))
            registeredConsumer = name;
        else
            logger.info("当前已有其他消费者注册，请耐心等待退出");
    }

    // 取消注册消费者
    public static void unregisterConsumer() {
        registeredConsumer = null;
    }

    // 检查当前是否有消费者注册
    private static boolean isConsumerRegistered(String name) {
        return registeredConsumer == null || registeredConsumer.equals(name);
    }
}