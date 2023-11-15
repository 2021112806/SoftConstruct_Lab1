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

/**
 * created by meizhimin on 2021/4/22
 */
public class User {
    private final String name;
    private final List<String> messageList;

    public User(String name){
        this.name = name;
        this.messageList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    //订阅消息
    public  void subscribe(String platformName) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), Config.SERVICE_PORT);
        try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            out.println(Config.SUBSCRIBE+"\t"+this.name+"\t"+platformName);
            out.flush();
        }
    }

    //获取消息
    public List<String> get() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), Config.SERVICE_PORT);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            //先发送获取命令
            out.println(Config.GET+"\t"+this.name);
            out.flush();
            //得到消息
            String message;
            message = in.readLine();
            while (!message.equals("END")){
                if(!messageList.contains(message)){
                    messageList.add(message);
                }
                message = in.readLine();
            }
            return messageList;
        }
    }
}
