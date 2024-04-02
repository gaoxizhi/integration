package net.gaox.search.constant;

/**
 * <p> 常量池 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-21 17:55
 */
public class Constants {

    /**
     * 测试索引名称，测试前需创建
     */
    public static final String TEST_INDEX_NAME = "product-test";

    /**
     * 14万商品索引名称，测试前需创建
     */
    public static final String INDEX_NAME = "product";

    /**
     * 批量插入大小
     */
    public static final Integer BATCH_SIZE = 1000;


    /**
     * 14万数据，将 resources/file/140k_products.zip 解压后放在file目录
     */
    public static final String PRODUCT_140K_FILE_NAME = "/Users/gaox/codeing/gitee/integration/file/140k_products.txt";

}
