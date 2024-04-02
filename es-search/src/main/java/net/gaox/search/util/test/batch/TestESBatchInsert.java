package net.gaox.search.util.test.batch;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.gaox.search.constant.Constants;
import net.gaox.search.entity.Product;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> 批量插入测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-21 19:45
 */
@Slf4j
public class TestESBatchInsert {

    private static RestHighLevelClient client =
            new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    private static final String indexName = Constants.INDEX_NAME;

    public static void main(String[] args) throws Exception {

        List<Product> productList = ProductReadUtil.file2List(Constants.PRODUCT_140K_FILE_NAME);
        log.info("准备数据 {} 条", productList.size());
        if (CollectionUtils.isEmpty(productList)) {
            throw new Exception("数据为空");
        }

        // 分批插入
        for (int i = 0; i < productList.size() / Constants.BATCH_SIZE + 1; i++) {
            List<Product> products = productList.stream()
                    .skip((long) i * Constants.BATCH_SIZE)
                    .limit(Constants.BATCH_SIZE)
                    .collect(Collectors.toList());
            log.info("第 {} 批{}条数据, ", i + 1, products.size());
            batchInsert(products);
        }

        client.close();
    }

    private static void batchInsert(List<Product> productList) throws Exception {
        BulkRequest request = new BulkRequest();
        productList.forEach(pro -> {
            IndexRequest indexRequest = new IndexRequest(indexName)
                    //此处的 .id方法 指定了就是自定义id 否则是用 ES生成id
                    .id(pro.getId().toString())
                    .source(JSONObject.toJSONString(pro), XContentType.JSON);
            request.add(indexRequest);
        });
        client.bulk(request, RequestOptions.DEFAULT);
        log.info("批量插入完成");
    }

}
