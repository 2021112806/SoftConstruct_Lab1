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
public class Model {
    private final String name;
    private final List<String> messageList;

    public Model(String name){
        this.name = name;
        this.messageList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    //获取消息
    public List<String> get(String platform) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), Config.SERVICE_PORT);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            //先发送获取命令
            out.println(Config.GET+"\t"+this.name+"\t"+platform);
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

