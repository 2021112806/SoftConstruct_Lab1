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

    Consumer consumer1=new Consumer("sh");
    Consumer consumer2=new Consumer("jsn");
    Consumer consumer3=new Consumer("xyq");


    Producer producer1=new Producer("producer1");
    Producer producer2=new Producer("producer2");
    Producer producer3=new Producer("producer3");

    @Test
    public void testPlatform() throws Exception{
        producer1.produce("23号去操场集合");
        producer2.produce("Hello world!");
        producer3.produce("今天是星期三");

        logger.info("SEND SUCCESS!");
    }

    @Test
    public void testUser() throws Exception{
        consumer1.register();
        String mes1=consumer1.consume();
        consumer1.unregister();

        String mes2=consumer2.consume();
        consumer2.register();
        consumer3.register();
        String mes3=consumer3.consume();

        logger.info(consumer1.getName()+"收到的信息为"+ mes1);
        logger.info(consumer2.getName()+"收到的信息为"+ mes2);
        logger.info(consumer3.getName()+"收到的信息为"+ mes3);
    }
}
