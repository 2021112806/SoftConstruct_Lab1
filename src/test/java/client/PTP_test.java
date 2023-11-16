package client;

import com.zdsn.mq.client.Consumer;
import com.zdsn.mq.client.Producer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试MQ+
 */
public class PTP_test {
    private static final Logger logger = LoggerFactory.getLogger(PTP_test.class);

    private final Consumer consumer1=new Consumer("sh");
    private final Consumer consumer2=new Consumer("jsn");
    private final Consumer consumer3=new Consumer("xyq");


    private final Producer producer1=new Producer("Producer1");
    private final Producer producer2=new Producer("Producer2");
    private final Producer producer3=new Producer("Producer3");

    @Test
    public void testPlatform() throws Exception{
        producer1.produce("23号去操场集合");
        producer2.produce("Hello world!");
        producer3.produce("今天是星期三");
        //消息队列容量已满，无法储存该消息
        producer3.produce("今天是星期五");

        logger.info("SEND SUCCESS!");
    }

    @Test
    public void testUser() throws Exception{
        //注册-消费-退出
        consumer1.register();
        String mes1=consumer1.consume();
        consumer1.unregister();

        //未注册无法消费消息
        String mes2=consumer2.consume();

        //已有用户注册无法再注册
        consumer2.register();
        consumer3.register();

        String mes3=consumer3.consume();

        logger.info(consumer1.getName()+"收到的信息为"+ mes1);//应为：23号去操场集合
        logger.info(consumer2.getName()+"收到的信息为"+ mes2);//应为null
        logger.info(consumer3.getName()+"收到的信息为"+ mes3);//应为null
    }
}
