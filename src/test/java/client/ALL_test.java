package client;

import com.zdsn.mq.client.Model;
import com.zdsn.mq.client.Publisher;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 测试MQ
 */
public class ALL_test {
    private static final Logger logger = LoggerFactory.getLogger(PS_test.class);

    private final Publisher publisher1 = new Publisher("Platform1");
    private final Publisher publisher2 = new Publisher("Platform2");

    private final Model user1 = new Model("sh");
    private final Model user2 = new Model("jsn");
    private final Model user3 = new Model("xyq");

    @Test
    public void testPlatform() throws Exception{
        publisher1.publish("19号去操场集合");
        publisher1.publish("24号有比赛");
        publisher2.publish("23号去诚意501研讨");
        publisher2.publish("26号有考试");
        logger.info("SEND SUCCESS!");
    }

    @Test
    public void testUser() throws Exception{
        List<String> messageList_0= user1.get("Platform1");
        List<String> messageList_1= user2.get("Platform2");
        List<String> messageList_2= user3.get("Platform1");
        logger.info(user1.getName()+"收到的信息列表为"+ messageList_0);
        logger.info(user2.getName()+"收到的信息列表为"+ messageList_1);
        logger.info(user3.getName()+"收到的信息列表为"+ messageList_2);
    }
}
