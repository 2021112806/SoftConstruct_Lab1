package client;

import com.zdsn.mq.client.Model;
import com.zdsn.mq.client.Platform;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 测试MQ
 */
public class ALL_test {

    private static final Logger logger = LoggerFactory.getLogger(test.class);
    private Platform platform_0 = new Platform("Platform_0");
    private Platform platform_1 = new Platform("Platform_1");
    private Model user_0 = new Model("小华");
    private Model user_1 = new Model("小明");
    private Model user_2 = new Model("小红");

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
        List<String> messageList_0=user_0.get("Platform_0");
        List<String> messageList_1=user_1.get("Platform_1");
        List<String> messageList_2=user_2.get("Platform_0");
        logger.info(user_0.getName()+"收到的信息列表为"+ messageList_0);
        logger.info(user_1.getName()+"收到的信息列表为"+ messageList_1);
        logger.info(user_2.getName()+"收到的信息列表为"+ messageList_2);
    }
}
