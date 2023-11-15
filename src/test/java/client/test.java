package client;

import com.zdsn.mq.client.Platform;
import com.zdsn.mq.client.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 测试MQ
 */
public class test {

    private static final Logger logger = LoggerFactory.getLogger(test.class);
    private Platform platform_0 = new Platform("Platform_0");
    private Platform platform_1 = new Platform("Platform_1");
    private User user_0 = new User("小华");
    private User user_1 = new User("小明");
    private User user_2 = new User("小红");

    @Test
    public void testPlatform() throws Exception{
        platform_0.produce("23号去操场集合");
        platform_0.produce("24号有体侧");
        platform_1.produce("23号去正心开会");
        platform_1.produce("24号有考试");
        logger.info("SEND SUCCESS!");
    }

    @Test
    public void testUser() throws Exception{
        user_0.subscribe("Platform_0");
        user_1.subscribe("Platform_1");
        user_2.subscribe("Platform_0");
        user_2.subscribe("Platform_1");
        List<String> messageList_0=user_0.get();
        List<String> messageList_1=user_1.get();
        List<String> messageList_2=user_2.get();
        logger.info(user_0.getName()+"收到的信息列表为"+String.valueOf(messageList_0));
        logger.info(user_1.getName()+"收到的信息列表为"+String.valueOf(messageList_1));
        logger.info(user_2.getName()+"收到的信息列表为"+String.valueOf(messageList_2));
    }
}
