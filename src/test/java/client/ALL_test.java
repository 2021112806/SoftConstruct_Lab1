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
    private Publisher publisher_0 = new Publisher("Platform_0");
    private Publisher publisher_1 = new Publisher("Platform_1");
    private Model user_0 = new Model("小华");
    private Model user_1 = new Model("小明");
    private Model user_2 = new Model("小红");

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
        List<String> messageList_0=user_0.get("Platform_0");
        List<String> messageList_1=user_1.get("Platform_1");
        List<String> messageList_2=user_2.get("Platform_0");
        logger.info(user_0.getName()+"收到的信息列表为"+ messageList_0);
        logger.info(user_1.getName()+"收到的信息列表为"+ messageList_1);
        logger.info(user_2.getName()+"收到的信息列表为"+ messageList_2);
    }
}
