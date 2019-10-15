package net.gaox.shirojwt.util.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * <p> 自定义JWTToken </p>
  JWTToken
 * @author gaox·Eric
 * @date 2019/5/4 00:53
 */
public class JWTToken implements AuthenticationToken {

    // 密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }
    @Override
    public Object getCredentials() {
        return token;
    }
}