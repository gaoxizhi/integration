package net.gaox.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p> 邮件信息vo </p>
 *
 * @author gaox·Eric
 * @date 2020/3/24 10:53
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class MailInfo {
    /**
     * 发件人
     */
    private String from;
    /**
     * 收件人
     */
    private String to;
    /**
     * 主题
     */
    private String subject;
    /**
     * 内容
     */
    private String context;
    /**
     * 已读回执
     */
    private Boolean readReceipt;
}