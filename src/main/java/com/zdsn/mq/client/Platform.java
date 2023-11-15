package com.zdsn.mq.client;

import com.zdsn.mq.config.Config;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * created by meizhimin on 2021/4/22
 */
public class Platform {
    private final String name;

    public Platform(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    //生产消息
    public void produce(String message) throws Exception {
        //本地的的 BrokerServer.SERVICE_PORT 创建SOCKET
        Socket socket = new Socket(InetAddress.getLocalHost(), Config.SERVICE_PORT);
        // try with resource写法，以便于关闭IO流
        try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            out.println(Config.PUBLISH + "\t"+this.getName()+"\t"+message);
            out.flush();
        }
    }
}
