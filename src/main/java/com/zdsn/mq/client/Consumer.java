package com.zdsn.mq.client;

import com.zdsn.mq.config.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Consumer {
    private final String name;

    public Consumer(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //消费消息ss
    public String consume() throws IOException{
        @SuppressWarnings("resource")
        Socket socket = new Socket(InetAddress.getLocalHost(), Config.SERVICE_PORT);
        try (BufferedReader in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out=new PrintWriter(socket.getOutputStream())) {
            //先向消息列队发送字符串“CONSUME”表示消费
            out.println(Config.CONSUME + "\t" + this.name);
            out.flush();
            //再从消息列队获取一条消息
            return in.readLine();
        }
    }

    //注册
    public void register() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), Config.SERVICE_PORT);
        try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            out.println(Config.REGISTER+"\t"+this.name);
            out.flush();
        }
    }

    //退出
    public void unregister() throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), Config.SERVICE_PORT);
        try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            out.println(Config.UNREGISTER);
            out.flush();
        }
    }
}
