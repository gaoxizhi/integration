package net.gaox.regexs;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2024-07-03 14:37
 */
@Slf4j
public class RegexExample {
    static String[] urls = {
            "google.comn",
            "google.com.cn",
            "v1.mail.google.com",
            "google.com",
            "mail.google.com",
            "www.google.com",
            "v2.mail.google.com",
            "v5.mail.google.com"
    };
    public static void main(String[] args) {
        String baseWord = "google.com";
        String regex = "^(?:[a-z0-9]+(?:-[a-z0-9]+)*\\.)*" + "(?:" + baseWord + ")" + "(?:\\.[a-z]{2,})?$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        for (String url : urls) {
            Matcher matcher = pattern.matcher(url);
            if (matcher.matches()) {
                log.info("Matched: {}", url);
            } else {
                log.info("Not Matched: {}", url);
            }
        }
    }
}