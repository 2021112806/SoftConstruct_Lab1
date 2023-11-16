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

    private final Publisher publisher1 = new Publisher("Platform1");
    private final Publisher publisher2 = new Publisher("Platform2");

    private final Subscriber subscriber1 = new Subscriber("sh");
    private final Subscriber subscriber2 = new Subscriber("jsn");
    private final Subscriber subscriber3 = new Subscriber("xyq");

    @Test
    public void testPlatform() throws Exception{
        publisher1.publish("23号去操场集合");
        publisher1.publish("24号有体测");
        publisher2.publish("23号去正心708开会");
        publisher2.publish("24号有考试");
        logger.info("SEND SUCCESS!");
    }

    @Test
    public void testUser() throws Exception{
        subscriber1.subscribe("Platform1");
        subscriber2.subscribe("Platform2");
        subscriber3.subscribe("Platform1");
        subscriber3.subscribe("Platform2");
        List<String> messageList1= subscriber1.get();
        List<String> messageList2= subscriber2.get();
        List<String> messageList3= subscriber3.get();
        logger.info(subscriber1.getName()+"收到的信息列表为"+ messageList1);
        logger.info(subscriber2.getName()+"收到的信息列表为"+ messageList2);
        logger.info(subscriber3.getName()+"收到的信息列表为"+ messageList3);
    }
}
