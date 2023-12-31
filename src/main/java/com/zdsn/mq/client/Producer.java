package com.zdsn.mq.client;

import com.zdsn.mq.config.Config;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Producer extends Platform{
    public Producer(String name){
        super(name);
    }

    //生产消息
    public void produce(String message) throws Exception {
        //本地的的 BrokerServer.SERVICE_PORT 创建SOCKET
        Socket socket = new Socket(InetAddress.getLocalHost(), Config.SERVICE_PORT);
        // try with resource写法，以便于关闭IO流
        try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            out.println(Config.PRODUCE + "\t"+this.getName()+"\t"+message);
            out.flush();
        }
    }
}
