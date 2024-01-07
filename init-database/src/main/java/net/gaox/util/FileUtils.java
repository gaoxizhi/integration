package net.gaox.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * <p> 文件工具类 </p>
 *
 * @author gaox·Eric
 * @date 2023-03-25 16:38
 */
@Slf4j
public class FileUtils {

    /**
     * 写 配置文件 键值对
     *
     * @param fileName 文件名
     * @param kv       键值对
     * @return 写入状态
     */
    public static boolean setPropertyFile(String fileName, Map<String, String> kv) {
        String filePath = getFilePath(fileName);
        if (!CollectionUtils.isEmpty(kv)) {
            try {
                Properties prop = new Properties();
                InputStream input = new BufferedInputStream(new FileInputStream(filePath));
                prop.load(input);
                Set<String> keys = kv.keySet();
                keys.forEach(key -> prop.put(key, kv.get(key)));

                FileOutputStream output = new FileOutputStream(filePath);
                prop.store(output, "update properties");

                output.flush();
                output.close();
                input.close();
                return true;
            } catch (IOException e) {
                log.warn("文件写入异常： ", e);
            }
        }
        return false;
    }

    /**
     * 读 配置文件 键值对
     *
     * @param fileName 文件名
     * @return 键值对
     */
    public static Properties loadPropertyFile(String fileName) {
        String path = getFilePath(fileName);
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream(path);
            prop.load(input);
            return prop;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取文件路径
     *
     * @param fileName 文件名
     * @return 文件路径
     */
    private static String getFilePath(String fileName) {
        return FileUtils.class.getClassLoader().getResource(fileName).getPath();
    }

    /**
     * 计算 文本长度
     *
     * @param content 文本
     * @return 长度
     */
    public static int getWords(String content) {
        int count;
        String cn_words = content.replaceAll("[^(\\u4e00-\\u9fa5，。《》？；’‘：“”【】、）（……￥！·)]", "");
        int cn_words_count = cn_words.length();
        String non_cn_words = content.replaceAll("[^(a-zA-Z0-9`\\-=\';.,/~!@#$%^&*()_+|}{\":><?\\[\\])]", " ");
        int non_cn_words_count = 0;
        String[] temp = non_cn_words.split(" ");
        for (String ch : temp) {
            if (ch.trim().length() != 0) {
                non_cn_words_count++;
            }
        }
        count = cn_words_count + non_cn_words_count;
        return count;
    }

}
