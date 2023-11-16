package com.zdsn.mq.MQStart;

import com.zdsn.mq.config.Config;
import com.zdsn.mq.controller.ALL_Broker;
import com.zdsn.mq.controller.PS_Broker;
import com.zdsn.mq.controller.PTP_Broker;
import com.zdsn.mq.server.BrokerServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.net.ServerSocket;
import java.util.Scanner;

/**
 * 启动加载MQ
 */

@Component
public class loadMq implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(loadMq.class);

    @Override
    public void run(ApplicationArguments applicationArguments){
        logger.info("启动加载MQ  -- START");
        Scanner in = new Scanner(System.in);
        logger.info("请输入你想选择的调度策略：");
        logger.info("1.全广播式");
        logger.info("2.选择广播式-点对点");
        logger.info("3.选择广播式-发布订阅");
        String Choice = in.next();
        switch (Choice) {
            case "1":
                logger.info("开启全广播式调度策略");
                try {
                    ServerSocket server1 = new ServerSocket(Config.SERVICE_PORT);
                    while (true) {
                       BrokerServer brokerServer = new BrokerServer(server1.accept(), new ALL_Broker());
                        new Thread(brokerServer).start();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            case "2":
                logger.info("开启选择广播式-点对点调度策略");
                try {
                    ServerSocket server2 = new ServerSocket(Config.SERVICE_PORT);
                    while (true) {
                        BrokerServer brokerServer=new BrokerServer(server2.accept(), new PTP_Broker());
                        new Thread(brokerServer).start();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            case "3":
                logger.info("开启选择广播式-发布订阅调度策略");
                try {
                    ServerSocket server3 = new ServerSocket(Config.SERVICE_PORT);
                    while (true) {
                        BrokerServer PSBrokerServer = new BrokerServer(server3.accept(), new PS_Broker());
                        new Thread(PSBrokerServer).start();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            default:
                logger.info("无效的选择，请重新输入");
                break;
        }

    }
}