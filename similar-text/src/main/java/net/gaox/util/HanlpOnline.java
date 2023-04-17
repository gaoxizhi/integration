package net.gaox.util;

import com.hankcs.hanlp.restful.HanLPClient;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.gaox.config.properties.HanlpProlerties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * <p> hanlp 在线调用 </p>
 *
 * @author gaox·Eric
 * @date 2023-04-17 21:06
 */
@Slf4j
@Component
@AllArgsConstructor
public class HanlpOnline {
    private HanlpProlerties hanlpProlerties;
    private static String url;
    private static String auth;

    @PostConstruct
    public void init() {
        url = hanlpProlerties.getUrl();
        auth = hanlpProlerties.getAuth();
    }

    /**
     * 在线解析
     *
     * @param text 文本
     * @return 模型
     */
    @SneakyThrows
    public static Map<String, List> onlineParse(String text) {
        HanLPClient client = new HanLPClient(url, auth);
        Map<String, List> parse = client.parse(text);
        return parse;
    }
}
