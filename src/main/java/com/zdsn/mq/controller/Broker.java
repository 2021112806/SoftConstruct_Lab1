package com.zdsn.mq.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息处理中心
 */
public interface Broker {
    // 记录此类产生的logger
    Logger logger = LoggerFactory.getLogger(Broker.class);
    Map<String,List<String>> subscribeMap = new ConcurrentHashMap<>();
    Map<String,List<String>> receivedMessages = new ConcurrentHashMap<>();

    default void handlePublish(String platformName, String message){
        // 对于新平台，添加key-value对
        if (!receivedMessages.containsKey(platformName)){
            List<String> messageList = new ArrayList<>();
            messageList.add(message);
            receivedMessages.put(platformName,messageList);
        }else{
            // 老平台，且没有重复发送消息，则直接在Value里面增加meg
            List<String> messageList = receivedMessages.get(platformName);
            if(!messageList.contains(message)){
                messageList.add(message);
            }
        }
    }

    default void handleSubscribe(String userName, String platformName){
        // 对于新平台，添加key-value对
        if (!subscribeMap.containsKey(userName)){
            List<String> platformList = new ArrayList<>();
            platformList.add(platformName);
            subscribeMap.put(userName,platformList);
        }else{
            // 老平台,且没有重复订阅,则直接在Value里面增加meg
            List<String> platformList = subscribeMap.get(userName);
            if(!platformList.contains(platformName)){
                platformList.add(platformName);
            }
        }
    }

    default List<String> handleGet(String userName){
        List<String> result = new ArrayList<>();
        List<String> platformList = subscribeMap.get(userName);
        for(String platformName:platformList){
            List<String> messageList = receivedMessages.get(platformName);
            for(String message:messageList){
                result.add(platformName+"\t"+message);
            }
        }
        return result;
    }

    String handleConsume(String userName);

    void handleRegister(String userName);

    void handleUnregister();

    void handleProduce(String str);
}
