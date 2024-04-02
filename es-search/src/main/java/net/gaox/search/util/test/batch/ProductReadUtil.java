package net.gaox.search.util.test.batch;

import net.gaox.search.entity.Product;
import org.apache.commons.io.FileUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 产品 文件读取工具类 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-21 20:28
 */
public class ProductReadUtil {

    /**
     * 读取文件内容，转换为 list
     *
     * @param fileName 文件名
     * @return List<Product>
     * @throws Exception
     */
    public static List<Product> file2List(String fileName) throws Exception {
        File file = new File(fileName);
        List<String> list = FileUtils.readLines(file, "utf-8");
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }
        List<Product> productList = list.parallelStream()
                .map(s -> s.split(","))
                .map(s -> Product.builder()
                        .id(Integer.parseInt(s[0]))
                        .name(s[1])
                        .category(s[2])
                        .price(new BigDecimal(s[3]))
                        .place(s[4])
                        .code(s[5]).build())
                .collect(Collectors.toList());
        return productList;
    }
}
