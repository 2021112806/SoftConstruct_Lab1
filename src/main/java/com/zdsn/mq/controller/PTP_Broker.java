package com.zdsn.mq.controller;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class PTP_Broker implements Broker{
    //消息队列存储最大值
    private final static int MAX_SIZE=3;
    //保存消息数据的容器
    private static final ArrayBlockingQueue<String> messageQueue=new ArrayBlockingQueue<>(MAX_SIZE);
    // 当前注册的消费者
    private static String registeredConsumer = null;

    // 检查当前是否有消费者注册
    private static boolean isConsumerRegistered(String name) {
        return registeredConsumer == null || registeredConsumer.equals(name);
    }

    @Override
    public void handlePublish(String platformName, String message) {
    }

    @Override
    public void handleSubscribe(String userName, String platformName) {}

    @Override
    public List<String> handleGet(String userName) {
        return null;
    }

    @Override
    public String handleConsume(String userName) {
        String msg = null;
        if (registeredConsumer == null){
            return null;
        }
        if (registeredConsumer.equals(userName)) {
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

    @Override
    public void handleRegister(String userName) {
        if (isConsumerRegistered(userName))
            registeredConsumer = userName;
        else
            logger.info("当前已有其他消费者注册，请耐心等待退出");
    }

    @Override
    public void handleUnregister() {
        registeredConsumer = null;
    }

    @Override
    public void handleProduce(String msg) {
        if (messageQueue.offer(msg)) {
            logger.info("消息发送成功，msg："+msg+",暂存队列中的消息数量是:"+messageQueue.size());
        }else{
            logger.info("消息处理数据中心数据达到最大负荷，不能继续放入消息");
        }
    }
}