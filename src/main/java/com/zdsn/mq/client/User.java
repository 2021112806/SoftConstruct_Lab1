package com.zdsn.mq.client;

import com.zdsn.mq.config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
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
