package com.zdsn.mq.server;


import com.zdsn.mq.config.Config;
import com.zdsn.mq.controller.ALL_Broker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * 消息处理中心服务 BrokerServer
 * 用于启动消息处理中心
 */
public class ALL_BrokerServer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ALL_BrokerServer.class);

    private final Socket socket;

    public ALL_BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            while (true) {
                String str = in.readLine();
                if (str == null) {
                    continue;
                }
                logger.info("Broker接收到指令：" + str);
                if (str.startsWith(Config.PUBLISH)) {
                    //CONSUME 表示要消费一条消息
                    // 从消息队列中消费一条消息
                    String [] strs=str.split("\t",3);
                    String platformName = strs[1];
                    String message = strs[2];
                    ALL_Broker.handlePublish(platformName,message);
                    logger.info("成功publish，此时的信息列表为："+ALL_Broker.receivedMessages);

                } else if (str.startsWith(Config.GET)) {
                    String [] strs = str.split("\t",3);
                    String userName = strs[1];
                    String platformName = strs[2];
                    List<String> result;
                    result = ALL_Broker.handleGet(platformName);
                    logger.info("处理Get，此时的结果列表为："+result);
                    for(String meg:result){
                        out.println(meg);
                        out.flush();
                    }
                    out.println("END");
                    out.flush();
                } else {
                    logger.info("指令:" + str + "没有遵循协议,请修改格式！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}