package com.zdsn.mq.controller;

import java.util.List;

public class PS_Broker implements Broker{
    @Override
    public void handlePublish(String platformName, String message) {
        // 对于新平台，添加key-value对
        Broker.super.handlePublish(platformName, message);
    }

    @Override
    public void handleSubscribe(String userName, String platformName) {
        // 对于新平台，添加key-value对
        Broker.super.handleSubscribe(userName, platformName);
    }

    @Override
    public List<String> handleGet(String userName) {
        return Broker.super.handleGet(userName);
    }

    @Override
    public String handleConsume(String userName) {
        return null;
    }

    @Override
    public void handleRegister(String userName) {

    }

    @Override
    public void handleUnregister() {

    }

    @Override
    public void handleProduce(String str) {

    }
}

