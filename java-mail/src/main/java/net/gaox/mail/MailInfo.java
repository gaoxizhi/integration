package net.gaox.mail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

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
    private String[] recipients;
    /**
     * 抄送人
     */
    private String[] carbonCopy;
    /**
     * 主题
     */
    private String subject;
    /**
     * 内容
     */
    private String context;
    /**
     * 是否包含附件
     */
    private Boolean accessory;
    /**
     * 附件列表
     */
    private List<Accessory> accessoryList;
    /**
     * 是否html类型
     */
    private Boolean html;
    /**
     * 已读回执
     */
    private Boolean readReceipt;
}