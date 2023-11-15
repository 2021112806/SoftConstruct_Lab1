package com.zdsn.mq.server;

import com.zdsn.mq.config.Config;
import com.zdsn.mq.controller.PTP_Broker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class PTP_BrokerServer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(PTP_BrokerServer.class);
    private final Socket socket;

    public PTP_BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            while (true) {
                String str = in.readLine();
                if (str == null) {
                    continue;
                }
                logger.info("接受到原始数据：" + str);
                if (str.startsWith(Config.CONSUME)) {
                    //consume表示需要消费一条消息
                    String [] strs=str.split("\t",2);
                    String userName = strs[1];
                    String meString = PTP_Broker.consume(userName);
                    out.println(meString);
                    out.flush();
                } else if (str.startsWith(Config.REGISTER)){
                    String [] strs=str.split("\t",2);
                    String userName = strs[1];
                    PTP_Broker.registerConsumer(userName);
                    logger.info("成功register，注册消费者为："+userName);
                } else if (str.startsWith(Config.UNREGISTER)){
                    PTP_Broker.unregisterConsumer();
                    logger.info("消费者已退出注册");
                }else {
                    //其他情况都表示生产消息放到消息队列中
                    PTP_Broker.produce(str);
                }
            }
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }
}
