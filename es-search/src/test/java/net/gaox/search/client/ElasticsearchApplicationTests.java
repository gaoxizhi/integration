package net.gaox.search.client;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.gaox.search.constant.Constants;
import net.gaox.search.entity.Product;
import net.gaox.search.util.test.batch.ProductReadUtil;
import net.minidev.json.JSONValue;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * <p> Client 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-21 20:02
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticsearchApplicationTests {

    @Autowired
    private RestHighLevelClient client;
    private static final String indexName = Constants.TEST_INDEX_NAME;

    @Test
    public void testCreateIndex() throws IOException {
        // 1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        // 2.客户端执行请求IndicesClient，执行create方法创建索引，请求后获得响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        log.info("创建状态={}", response.isAcknowledged());
    }

    @Test
    public void testExistIndex() throws IOException {
        // 1.查询索引请求
        GetIndexRequest request = new GetIndexRequest(indexName);
        // 2.执行exists方法判断是否存在
        boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);
        log.info("索引[{}]{}", indexName, exists ? "已存在" : "不存在");
    }

    // @Test
    public void testDeleteIndex() throws IOException {
        // 删除索引请求
        DeleteIndexRequest request = new DeleteIndexRequest(indexName);
        // 执行delete方法删除指定索引
        AcknowledgedResponse delete = client.indices().delete(request, RequestOptions.DEFAULT);
        log.info("删除索引[{}]{}", indexName, delete.isAcknowledged() ? "成功" : "失败");
    }

    @Test
    public void testAddProduct() throws IOException {
        // 1.创建对象
        Product product = Product.builder()
                .id(1)
                .name("连衣裙")
                .category("女装")
                .price(new BigDecimal("99.99"))
                .place("浙江杭州")
                .code("690212131012")
                .build();
        // 2.创建请求
        IndexRequest request = new IndexRequest(indexName)
                .id("1").timeout("1s");

        // 4.将数据放入请求，要将对象转化为json格式
        request.source(JSONValue.toJSONString(product), XContentType.JSON);

        // 5.客户端发送请求，获取响应结果
        IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
        log.info("文档[id={}]添加{}, response={}", product.getId(),
                RestStatus.OK.equals(indexResponse.status()) ? "成功" : "失败", indexResponse);
    }

    @Test
    public void testGetProduct() throws IOException {
        // 1.创建请求,指定索引、文档id
        String id = "1";
        GetRequest request = new GetRequest(indexName, id);
        GetResponse getResponse = client.get(request, RequestOptions.DEFAULT);

        // 返回的类型是Map集合，可以通过反序列化到对象
        Product product = JSONObject.parseObject(getResponse.getSourceAsBytes(), Product.class);

        log.info("{}id={}的商品", getResponse.isExists() ? "查找到" : "未找到", id);
        if (getResponse.isExists()) {
            log.info("商品信息：{}", product);
        }
    }

    @Test
    public void testUpdateProduct() throws IOException {
        // 创建请求,指定索引、文档id
        String id = "1";
        UpdateRequest request = new UpdateRequest(indexName, id);
        Product product = Product.builder()
                .id(1)
                .name("连衣裙")
                .category("女装")
                .price(new BigDecimal("129"))
                .place("浙江杭州")
                .code("690212131012")
                .build();
        // 将创建的对象放入文档中
        request.doc(JSONValue.toJSONString(product), XContentType.JSON);

        UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
        // 更新成功返回OK
        log.info("商品id={}, {}", id, RestStatus.OK.equals(updateResponse.status()) ? "已更新" : "更新失败");
    }

    @Test
    public void testDeleteProduct() throws IOException {
        // 创建删除请求，指定要删除的索引与文档ID
        String id = "1";
        DeleteRequest request = new DeleteRequest(indexName, id);

        DeleteResponse updateResponse = client.delete(request, RequestOptions.DEFAULT);
        // 删除成功返回OK，没有找到返回NOT_FOUND
        RestStatus status = updateResponse.status();
        log.info("商品id={}, {}", id, RestStatus.OK.equals(status) ? "已删除" :
                RestStatus.NOT_FOUND.equals(status) ? "未找到" : "删除失败");
    }

    @Test
    public void testBulkAddProduct() throws Exception {
        BulkRequest bulkRequest = new BulkRequest(indexName);
        // 设置超时
        bulkRequest.timeout("10s");

        List<Product> products = ProductReadUtil.file2List(Constants.PRODUCT_140K_FILE_NAME);
        products.stream().limit(200).forEach(s ->
                bulkRequest.add(new IndexRequest(indexName)
                        .id(s.getId().toString())
                        .source(JSONValue.toJSONString(s), XContentType.JSON)));

        BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
        // 是否执行失败,false为执行成功
        log.info("执行结果：{}", bulkResponse.hasFailures() ? "成功" : "失败");
    }

    @Test
    public void testSearch() throws IOException {
        // 里面可以放多个索引
        SearchRequest searchRequest = new SearchRequest(indexName);

        // 构造搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 此处可以使用QueryBuilders工具类中的方法
        // 1.查询所有
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        // 2.查询name和category中含有"汽车"的
        sourceBuilder.query(QueryBuilders.multiMatchQuery("汽车", "name", "category"));
        // 3.分页查询，大于10条默认只返回10条
        sourceBuilder.from(0).size(10);

        // 4.按照score正序排列
        // sourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.ASC));
        // 5.按照id倒序排列（score会失效返回NaN）
        // sourceBuilder.sort(SortBuilders.fieldSort("_id").order(SortOrder.DESC));

        // 6.给指定字段加上指定高亮样式
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name").preTags("<span style='color:red;'>").postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // 获取总条数
        long hitCount = searchResponse.getHits().getTotalHits().value;
        log.info("匹配到{}件商品.", hitCount);
        // 获取匹配到的数据集
        SearchHit[] hits = searchResponse.getHits().getHits();
        Arrays.stream(hits).forEach(s -> {
            Product product = JSONObject.parseObject(s.getSourceAsString(), Product.class);
            log.info("找到商品id={}, 在索引={},匹配度={}, 商品详情[{}].",
                    product.getId(), s.getIndex(), s.getScore(), product);
        });
    }

}
