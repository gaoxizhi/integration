package net.gaox.util;

import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;

/**
 * <p> 文本相似度测试 </p>
 *
 * @author gaox·Eric
 * @date 2023-04-17 21:03
 */
@Slf4j
public class TextComparatorTest extends TestCase {

    public void test() {
        String textA = "https://www.baidu.com/s?&ie=utf-8&rsv_pq=a960050110000428b&oq=cvd1810-wj型号导航&wd=中国式现代化造福中国人民";
        String textB = "https://www.baidu.com/s?wd=美国式现代化造福中人民&ie=utf-8&rsv_pq=a916005010000428b&oq=cvd1810-wj型号导航";
        Double cosineSimilarity = TextComparator.getCosineSimilarity(textA, textB);
        log.info("text a :--> {}", textA);
        log.info("text b :--> {}", textB);
        log.info("cosine similarity = {}", cosineSimilarity);
    }

}