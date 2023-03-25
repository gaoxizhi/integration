package net.gaox.domain.dto;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p> 初始化配置信息 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 16:30
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@Builder
@EqualsAndHashCode
public class InitializeDTO {

    private String host;
    private String port;
    private String db;
    private String username;
    private String password;
    private String title;
    private String domain;
    private String adminAccount;
    private String adminPassword;
    private String adminEmail;

}
