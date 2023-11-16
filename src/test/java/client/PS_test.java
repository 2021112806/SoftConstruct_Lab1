package client;

import com.zdsn.mq.client.Publisher;
import com.zdsn.mq.client.Subscriber;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 测试MQ
 */
public class PS_test {

    private static final Logger logger = LoggerFactory.getLogger(PS_test.class);
    private Publisher publisher_0 = new Publisher("Platform_0");
    private Publisher publisher_1 = new Publisher("Platform_1");
    private Subscriber subscriber_0 = new Subscriber("小华");
    private Subscriber subscriber_1 = new Subscriber("小明");
    private Subscriber subscriber_2 = new Subscriber("小红");

    @Test
    public void testPlatform() throws Exception{
        publisher_0.publish("23号去操场集合");
        publisher_0.publish("24号有体侧");
        publisher_1.publish("23号去正心开会");
        publisher_1.publish("24号有考试");
        logger.info("SEND SUCCESS!");
    }

    @Test
    public void testUser() throws Exception{
        subscriber_0.subscribe("Platform_0");
        subscriber_1.subscribe("Platform_1");
        subscriber_2.subscribe("Platform_0");
        subscriber_2.subscribe("Platform_1");
        List<String> messageList_0= subscriber_0.get();
        List<String> messageList_1= subscriber_1.get();
        List<String> messageList_2= subscriber_2.get();
        logger.info(subscriber_0.getName()+"收到的信息列表为"+String.valueOf(messageList_0));
        logger.info(subscriber_1.getName()+"收到的信息列表为"+String.valueOf(messageList_1));
        logger.info(subscriber_2.getName()+"收到的信息列表为"+String.valueOf(messageList_2));
    }
}
