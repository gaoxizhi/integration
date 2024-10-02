package net.gaox;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2024-08-25 14:01
 */
@Slf4j
public class SensitiveWord {
    private Map sensitiveWordMap;

    private void addSensitiveWord(Set<String> words) {
        sensitiveWordMap = new HashMap<>(words.size());
        String key;
        Map nowMap;
        for (String word : words) {
            key = word;
            nowMap = sensitiveWordMap;
            for (int i = 0; i < key.length(); i++) {
                char c = key.charAt(i);
                Object obj = nowMap.get(c);
                if (obj == null) {
                    Map newMap = new HashMap<>();
                    newMap.put("isEnd", "0");
                    nowMap.put(c, newMap);
                    nowMap = newMap;
                } else {
                    nowMap = (Map) obj;
                }
            }
        }
        log.info("sensitiveWordMap = {}", JSONObject.toJSONString(sensitiveWordMap));
    }

    private int check(String text, int startIndex, int matchType) {
        // 敏感词结束标志位，用于敏感词只有一个字时，判断是否为敏感词
        boolean flag = false;
        // 匹配默认匹配规则，最小规则
        int matchFlag = 0;
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for (int i = startIndex; i < text.length(); i++) {
            word = text.charAt(i);
            nowMap = (Map) nowMap.get(word);
            if (nowMap != null) {
                matchFlag++;
                if ("1".equals(nowMap.get("isEnd"))) {
                    flag = true;
                    if (matchType == 1) {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        if (2 > matchFlag || !flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }

    public static void main(String[] args) {
        SensitiveWord sensitiveWord = new SensitiveWord();

        HashSet<String> strings = new HashSet<String>() {{
            add("王八蛋");
            add("王八羔子");
        }};
        sensitiveWord.addSensitiveWord(strings);
        int checked = sensitiveWord.check("1王八蛋", 0, 1);
        log.info("checked = {}", checked);
    }

}
