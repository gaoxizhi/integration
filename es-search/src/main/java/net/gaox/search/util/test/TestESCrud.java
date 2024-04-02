package net.gaox.search.util.test;

import lombok.extern.slf4j.Slf4j;
import net.gaox.search.constant.Constants;
import net.gaox.search.entity.Product;
import org.apache.http.HttpHost;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p> 测试ES增删改查 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-21 17:01
 */
@Slf4j
public class TestESCrud {

    private static RestHighLevelClient client =
            new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

    public static void main(String[] args) throws Exception {

        int id = 1;
        Product product =
                Product.builder().id(id).name("product 1").build();

        // 创建文档
        addDocument(product);
        getDocument(id);

        // 修改文档
        product.setName("product 2");
        updateDocument(product);
        getDocument(id);

        // 删除文档
        deleteDocument(id);
        getDocument(id);

        client.close();
    }

    /**
     * 创建文档
     *
     * @param product 文档内容
     * @throws IOException 抛出异常
     */
    private static void addDocument(Product product) throws IOException {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("id", product.getId());
        jsonMap.put("name", product.getName());
        IndexRequest indexRequest = new IndexRequest(Constants.TEST_INDEX_NAME)
                .source(jsonMap);
        client.index(indexRequest, RequestOptions.DEFAULT);
        log.info("添加文档：{}", product);
    }

    /**
     * 获取文档
     *
     * @param id 文档id
     * @throws IOException 抛出异常
     */
    private static void getDocument(int id) throws IOException {
        GetRequest request = new GetRequest(Constants.TEST_INDEX_NAME, String.valueOf(id));
        GetResponse response = client.get(request, RequestOptions.DEFAULT);

        if (!response.isExists()) {
            log.warn("文档id={}, 不存在!", id);
        } else {
            String source = response.getSourceAsString();
            log.info("文档id={}内容是: {}.", id, source);
        }
    }

    /**
     * 修改文档
     *
     * @param product 文档内容
     * @throws IOException 抛出异常
     */
    private static void updateDocument(Product product) throws IOException {

        getDocument(product.getId());
        UpdateRequest updateRequest = new UpdateRequest(Constants.TEST_INDEX_NAME, String.valueOf(product.getId()))
                .doc("name", product.getName());

        client.update(updateRequest, RequestOptions.DEFAULT);
        log.info("文档id={}内容修改为: {}", product.getId(), product);
    }

    /**
     * 删除文档
     *
     * @param id 文档id
     * @throws IOException 抛出异常
     */
    private static void deleteDocument(int id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(Constants.TEST_INDEX_NAME, String.valueOf(id));

        client.delete(deleteRequest, RequestOptions.DEFAULT);
        log.info("删除文档id={}.", id);
    }

}
