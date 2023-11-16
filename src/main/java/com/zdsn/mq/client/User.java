package com.zdsn.mq.client;

import java.util.ArrayList;
import java.util.List;

public class User {
    protected final String name;
    protected final List<String> messageList;
    public User(String name){
        this.name = name;
        messageList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
}
