package net.gaox.mail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.MessagingException;


/**
 * <p> 邮件服务测试类 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/24 12:12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Resource
    private TemplateEngine templateEngine;

    @Test
    public void sendSimpleMail() {
        mailService.sendSimpleMail("gx_zone@163.com", "测试spring boot mail", "测试spring boot mial 内容");
    }

    @Test
    public void sendHtmlMail() throws MessagingException {

        String content = "<html>\n" +
                "<body>\n" +
                "<h1>html</h1>\n" +
                "<h3>hello world</h3>\n" +
                "<body>\n" +
                "</html>\n";

        mailService.sendHtmlMail("gx_zone@163.com", "这是一封HTML邮件", content);
    }

    @Test
    public void sendAttachmentsMail() throws MessagingException {
        String filePath = "src/main/resources/data/error.xlsx";
        String content = "<html>\n" +
                "<body>\n" +
                "<h1>附件传输</h1>\n" +
                "<h3>hello world</h3>\n" +
                "<body>\n" +
                "</html>\n";
        mailService.sendAttachmentsMail("gx_zone@163.com", "这是一封HTML邮件", content, filePath);
    }

    @Test
    public void sendInlinkResourceMail() {
        String imgPath = "src/main/resources/data/bug.JPG";
        String rscId = "gaox098";
        String content = "<html>" +
                "<body>" +
                "<h1>图片邮件</h1>" +
                "<h3>hello world</h3>" +
                "<img src='cid:" + rscId + "'></img>" +
                "<body>" +
                "</html>";

        mailService.sendInlinkResourceMail("gx_zone@163.com", "这是一封图片邮件", content, imgPath, rscId);
    }

    @Test
    public void testTemplateMailTest() throws MessagingException {
        Context context = new Context();
        context.setVariable("id", "098");

        String emailContent = templateEngine.process("emailTeplate", context);
        mailService.sendHtmlMail("gx_zone@163.com", "这是一封HTML邮件", emailContent);
    }
}