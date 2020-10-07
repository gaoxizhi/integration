package net.gaox.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * <p> 邮件服务 </p>
 *
 * @author gaox·Eric
 * @date 2020/3/24 11:52
 */
@Service
@Slf4j
public class MailService {


    @Value("${spring.mail.username}")
    private String from;

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * 简单文本邮件
     *
     * @param to      接收者邮件
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);

        mailSender.send(message);
    }

    /**
     * HTML 文本邮件
     *
     * @param to      接收者邮件
     * @param subject 邮件主题
     * @param content HTML内容
     * @throws MessagingException
     */
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(from);

        mailSender.send(message);
    }

    /**
     * 附件邮件
     *
     * @param to       接收者邮件
     * @param subject  邮件主题
     * @param content  HTML内容
     * @param filePath 附件路径
     * @throws MessagingException
     */
    public void sendAttachmentsMail(String to, String subject, String content,
                                    String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
        helper.setFrom(from);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = file.getFilename();
        helper.addAttachment(fileName, file);

        mailSender.send(message);
    }

    /**
     * 图片邮件
     *
     * @param to      接收者邮件
     * @param subject 邮件主题
     * @param content HTML内容
     * @param rscPath 图片路径
     * @param rscId   图片ID
     */
    public void sendInlinkResourceMail(String to, String subject, String content,
                                       String rscPath, String rscId) {
        log.info("发送静态邮件开始: {},{},{},{},{}", to, subject, content, rscPath, rscId);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            helper.setFrom(from);

            FileSystemResource res = new FileSystemResource(new File(rscPath));
            helper.addInline(rscId, res);
            mailSender.send(message);
            log.info("发送静态邮件成功!");
        } catch (MessagingException e) {
            log.info("发送静态邮件失败: ", e);
        }
    }

    /**
     * 发送邮件
     *
     * @param mail 实体类
     * @return 发送结果
     */
    public Boolean sendMail(MailInfo mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, mail.getHtml());
            //不指定发件邮箱，使用默认设置
            helper.setFrom((mail.getFrom().trim().isEmpty()) ? from : mail.getFrom());
            helper.setTo(mail.getRecipients());
            helper.setSubject(mail.getSubject());
            helper.setText(mail.getContext(), mail.getHtml());

            // 附件等需要在调用setText之后调用，否则邮件读取器可能无法正确解析内联引用
            if (mail.getAccessory()) {
                for (Accessory s : mail.getAccessoryList()) {
                    if (AccessoryType.PICTURE.equals(s.getType())) {
                        //图片
                        FileSystemResource res = new FileSystemResource(new File(s.getFilePath()));
                        helper.addInline(s.getPictureId(), res);
                    } else if (AccessoryType.FILE.equals(s.getType())) {
                        //附件
                        FileSystemResource file = new FileSystemResource(new File(s.getFilePath()));
                        String fileName = file.getFilename();
                        helper.addAttachment(fileName, file);
                    } else {
                        log.info("附件信息有误！");
                    }
                }
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            log.error("发送邮件失败！详情：\n{}", e.toString());
            return false;
        }

        return true;
    }
}