import com.Application;
import com.lance.business.controller.ApiController;
import com.lance.common.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertFalse;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class SpringTest
{
    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private ApiController apiController;

    @Test
    public void sendSimpleEmail()
    {
        String[] receiver = {"1234567890@qq.com"};
        String[] carbonCopy = {};
        String subject = "标题";
        String content = "内容";

        try
        {
            mailUtil.sendSimpleEmail(receiver, carbonCopy, subject, content);
        }
        catch (Exception e)
        {
            assertFalse("Send simple email failed", true);
            e.printStackTrace();
        }
    }

    @Test
    public void test()
    {
        apiController.sampleTest("55", null);
    }
}
