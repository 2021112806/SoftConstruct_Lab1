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
public class ALL_Broker {
    // 记录此类产生的logger
    private static final Logger logger = LoggerFactory.getLogger(ALL_Broker.class);
    public static final Map<String,List<String>> receivedMessages = new ConcurrentHashMap<>();

    public static void handlePublish(String platformName,String message){
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

    public static List<String> handleGet(String platformName){
        List<String> result = new ArrayList<>();
        List<String> messageList = receivedMessages.get(platformName);
        for(String message:messageList){
            result.add(platformName+"\t"+message);
        }
        return result;
    }
}

