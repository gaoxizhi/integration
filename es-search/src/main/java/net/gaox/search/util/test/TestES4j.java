package net.gaox.search.util.test;

import lombok.extern.slf4j.Slf4j;
import net.gaox.search.constant.Constants;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;

import java.io.IOException;

/**
 * <p> 连接测试-测试索引 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-21 16:50
 */
@Slf4j
public class TestES4j {

    private static RestHighLevelClient client =
            new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
    private final static String indexName = Constants.INDEX_NAME;

    public static void main(String[] args) throws IOException {
        if (!checkExistIndex(indexName)) {
            createIndex(indexName);
        }
        if (checkExistIndex(indexName)) {
            deleteIndex(indexName);
        }
        checkExistIndex(indexName);
        client.close();
    }

    /**
     * 检测索引是否存在
     *
     * @param indexName 索引名称
     * @return boolean
     * @throws IOException IOException
     */
    public static boolean checkExistIndex(String indexName) throws IOException {
        boolean result = true;
        try {
            OpenIndexRequest openIndexRequest = new OpenIndexRequest(indexName);
            result = client.indices().open(openIndexRequest, RequestOptions.DEFAULT).isAcknowledged();
        } catch (ElasticsearchStatusException ex) {
            String m = "type=index_not_found_exception, reason=no such index";
            if (ex.getMessage().contains(m)) {
                result = false;
            }
        }
        if (result) {
            log.info("索引[{}]存在", indexName);
        } else {
            log.info("索引[{}]不存在", indexName);
        }
        return result;

    }

    /**
     * 创建索引
     *
     * @param indexName 索引名称
     * @throws IOException IOException
     */
    public static void createIndex(String indexName) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        client.indices().create(request, RequestOptions.DEFAULT);
        log.info("创建索引[{}]", indexName);
    }

    /**
     * 删除索引
     *
     * @param indexName 索引名称
     * @throws IOException IOException
     */
    public static void deleteIndex(String indexName) throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        client.indices().delete(request, RequestOptions.DEFAULT);
        log.info("删除索引[{}]", indexName);

    }

}
