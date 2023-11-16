package com.zdsn.mq.controller;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息处理中心
 */
public class ALL_Broker implements Broker{
    @Override
    public void handlePublish(String platformName, String message) {
        Broker.super.handlePublish(platformName, message);
    }
    @Override
    public List<String> handleGet(String platformName){
        List<String> result = new ArrayList<>();
        List<String> messageList = receivedMessages.get(platformName);
        for(String message:messageList){
            result.add(platformName+"\t"+message);
        }
        return result;
    }
    @Override
    public void handleSubscribe(String userName, String platformName) {}
    @Override
    public String handleConsume(String userName) {
        return null;
    }

    @Override
    public void handleRegister(String userName) {}

    @Override
    public void handleUnregister() {}

    @Override
    public void handleProduce(String str) {}
}

