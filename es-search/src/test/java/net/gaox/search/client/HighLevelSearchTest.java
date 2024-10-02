package net.gaox.search.client;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import net.gaox.search.constant.Constants;
import net.gaox.search.entity.Product;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.*;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p> 多条件测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-02 21:22
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class HighLevelSearchTest {

    @Autowired
    private RestHighLevelClient client;


    /**
     * 多条件精确查询，取并集
     *
     * @throws IOException
     */
    @Test
    public void searchByUnionSet() throws IOException {
        // 根据ids，同理可以 通过id获取单条
        String[] ids = new String[]{"10127", "10128"};
        TermsQueryBuilder idsTermsQuery = QueryBuilders.termsQuery("_id", ids);
        IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery().addIds(ids);

        // 多条件查询，对多个字段
        TermsQueryBuilder unionSetQuery = QueryBuilders.termsQuery("name", "汽车", "轮胎");

        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(unionSetQuery);
        // builder.fetchSource(new String[]{"id", "category", "code", "price"}, new String[]{"name", "place"});
        // 如果包含和排除字段数组都有，则排除优先级更高
        builder.fetchSource(new String[]{"id", "category", "code", "price"}, new String[]{"name", "place", "code"});

        searchAndPrintRequest(builder);
    }

    /**
     * 多条件查询 + 排序 + 分页
     *
     * @throws IOException
     */
    @Test
    public void searchMultiMatch() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder();

        // 设置源字段过虑,第一个参数结果集包括哪些字段，第二个参数表示结果集不包括哪些字段
        // new SearchSourceBuilder()
        //         .fetchSource(new String[]{"_id","category", "code", "place", "price"}, new String[]{});

        // 创建请求时，链式设置条件
        // new SearchSourceBuilder().query(QueryBuilders.matchQuery("name", "汽车"));
        // 条件查询，单字段匹配
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("name", "汽车");
        // 条件查询，多字段匹配
        // MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery("汽车", "name");
        // 单字段匹配多条件
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name", "汽车"))
                .should(QueryBuilders.matchQuery("name", "卡车"));

        // 区间查询条件 range构造器
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("price").gt(100).lte(200);
        // form、to 构造器
        // QueryBuilders.rangeQuery("price").from(100).to(200, false);

        //条件搜索
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(matchQuery);
        boolQueryBuilder.must(rangeQuery);
        builder.query(boolQueryBuilder);

        //结果集合分页
        builder.from(0).size(20);

        //排序 字段的类型必须是：integer、double、long或者keyword
        builder.sort("price", SortOrder.ASC);

        searchAndPrintRequest(builder);
    }

    /**
     * nested类型嵌套查询
     *
     * @throws IOException
     */
    @Test
    public void searchNested() throws IOException {
        // 创建请求
        // {"query":{"bool":{"must":[
        // {"match":{"name":"OnitsukaTiger"}},
        // {"nested":{"path":"detail","query":{"bool":{"must":[{"match":{"detail.brand":"风格"}},{"match":{"detail.ingredient":"新疆"}}]}}}
        // }]}}}
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //条件查询
        BoolQueryBuilder mainBool = new BoolQueryBuilder();
        mainBool.must(QueryBuilders.matchQuery("name", "OnitsukaTiger"));

        //nested类型嵌套查询
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        boolQueryBuilder.must(QueryBuilders.matchQuery("detail.brand", "风格"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("detail.ingredient", "新疆"));
        NestedQueryBuilder nested = QueryBuilders.nestedQuery("detail", boolQueryBuilder, ScoreMode.None);
        mainBool.must(nested);

        builder.query(mainBool);

        searchAndPrintRequest(builder);
    }

    /**
     * 模糊查询
     *
     * @throws IOException
     */
    @Test
    public void searchFuzzyQuery() throws IOException {
        // 不使用通配符的模糊查询，左右匹配
        QueryStringQueryBuilder stringQuery = QueryBuilders.queryStringQuery("济南").field("place");
        // 前缀查询
        PrefixQueryBuilder prefixQuery = QueryBuilders.prefixQuery("place", "济");
        // 相似模糊查询
        FuzzyQueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("name", "摄像机器");
        // 模糊查询（字段是keyword），支持通配符
        WildcardQueryBuilder wildcardQuery = QueryBuilders.wildcardQuery("place", "广东*山");

        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().query(wildcardQuery);
        builder.from(2).size(300);
        searchAndPrintRequest(builder);
    }

    /**
     * 精确统计筛选文档数,查询性能有所降低
     *
     * @throws IOException
     */
    @Test
    public void searchTrackTotal() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder().trackTotalHits(true);

        //不输出原始数据
        builder.size(0);
        searchAndPrintRequest(builder);
    }

    /**
     * 聚合查询 sum
     *
     * @throws IOException
     */
    @Test
    public void searchAggregation() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder();

        //条件搜索
        builder.query(QueryBuilders.matchQuery("place", "山东济南"));
        //聚合查询
        AggregationBuilders.sum("sum_price").field("price");
        AggregationBuilders.avg("avg_price").field("price");
        AggregationBuilders.max("max_price").field("price");
        AggregationBuilders.min("min_price").field("price");
        builder.aggregation(AggregationBuilders.count("count_price").field("price"));

        // 构建 filters 聚合 for "from" field
        FiltersAggregationBuilder fromAgg = AggregationBuilders.filters("from_term_count",
                new FiltersAggregator.KeyedFilter("term1", QueryBuilders.termQuery("from", "term1")),
                new FiltersAggregator.KeyedFilter("term2", QueryBuilders.termQuery("from", "term2")),
                new FiltersAggregator.KeyedFilter("term3", QueryBuilders.termQuery("from", "term3")));


        //不输出原始数据
        builder.size(0);
        searchAndPrintRequest(builder);
    }

    /**
     * 分组聚合查询
     *
     * @throws IOException
     */
    @Test
    public void searchAggregationByGroup() throws IOException {
        // 创建请求
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //条件搜索
        builder.query(QueryBuilders.matchQuery("name", "摄像").operator(Operator.AND));
        //聚合查询
        AggregationBuilder aggregation = AggregationBuilders.terms("place_term").field("place.keyword")
                .subAggregation(AggregationBuilders.sum("sum_price").field("price"))
                .subAggregation(AggregationBuilders.avg("avg_price").field("price"))
                .subAggregation(AggregationBuilders.max("max_price").field("price"))
                .subAggregation(AggregationBuilders.min("min_price").field("price"));

        builder.aggregation(aggregation);

        //不输出原始数据
        builder.size(0);

        searchAndPrintRequest(builder);
    }

    private void searchAndPrintRequest(SearchSourceBuilder builder) throws IOException {
        SearchRequest searchRequest = new SearchRequest(Constants.INDEX_NAME);
        searchRequest.source(builder);
        log.info("search query = {}", builder);
        // 执行请求
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        printResponse(response);
    }

    private static void printResponse(SearchResponse response) {
        // //获取总条数
        long hitCount = response.getHits().getTotalHits().value;
        log.info("匹配到{}件商品.", hitCount);
        // 获取匹配到的数据集
        SearchHit[] hits = response.getHits().getHits();
        Arrays.stream(hits).forEach(s -> {
            Product product = JSONObject.parseObject(s.getSourceAsString(), Product.class);
            log.info("找到商品id={}, 在索引={},匹配度={}, 商品详情[{}].",
                    product.getId(), s.getIndex(), s.getScore(), product);
        });

        printAggregationsByString(response.getAggregations());
    }

    private static void printAggregationsByType(Aggregations aggregations) {
        Optional.ofNullable(aggregations).map(Aggregations::getAsMap)
                .ifPresent(map -> map.forEach((aggregationName, v) -> {
                    String aggregationContent;
                    if (v instanceof Terms) {
                        Terms terms = (Terms) v;
                        List<? extends Terms.Bucket> buckets = terms.getBuckets();
                        aggregationContent = buckets.stream().map(bucket -> {
                            // 分组的各项指标
                            String content = bucket.getAggregations().asMap().entrySet().stream()
                                    .map(r -> r.getKey() + "=" + getNumberValue(r.getValue()))
                                    .collect(Collectors.joining(", ", "{", "}"));
                            return String.format("[%s]共分为[%d]个分类: %s.",
                                    bucket.getKeyAsString(), bucket.getDocCount(), content);
                        }).collect(Collectors.joining("\n", "\n", ""));
                        log.info("\n聚合单元[{}]: {}", aggregationName, aggregationContent);
                    } else if (v instanceof Sum) {
                        Sum sum = (Sum) v;
                        log.info("[{}]: {}", aggregationName, sum.getValue());
                    } else if (v instanceof Min) {
                        Min min = (Min) v;
                        log.info("[{}]: {}", aggregationName, min.getValue());
                    } else if (v instanceof Max) {
                        Max max = (Max) v;
                        log.info("[{}]: {}", aggregationName, max.getValue());
                    } else if (v instanceof ValueCount) {
                        ValueCount count = (ValueCount) v;
                        log.info("[{}]: {}", aggregationName, count.getValue());
                    } else if (v instanceof Avg) {
                        Avg avg = (Avg) v;
                        log.info("[{}]: {}", aggregationName, avg.getValue());
                    } else {
                        log.error("没有匹配的[{}]", v.getClass());
                    }
                }));
    }

    private static void printAggregationsByString(Aggregations aggregations) {
        Optional.ofNullable(aggregations).ifPresent(s ->
                s.asMap().keySet().forEach(t ->
                        ((Terms) s.get(t)).getBuckets().forEach(r -> {
                            String content = r.getAggregations().asMap().entrySet().stream()
                                    .map(e -> e.getKey() + "=" + getNumberValue(e.getValue()))
                                    .collect(Collectors.joining(", ", "{", "}"));
                            log.info("聚合单元[{}]: {}", r.getKey(), content);
                        })));
    }

    /**
     * 获取聚合单元的数值
     *
     * @param aggregation value抽象对象
     * @return value str
     */
    public static String getNumberValue(Aggregation aggregation) {
        Number value = null;
        if (aggregation instanceof ParsedValueCount) {
            value = ((ParsedValueCount) aggregation).getValue();
        } else if (aggregation instanceof ParsedSum) {
            value = ((ParsedSum) aggregation).getValue();
        } else if (aggregation instanceof ParsedAvg) {
            value = ((ParsedAvg) aggregation).getValue();
        } else if (aggregation instanceof ParsedMax) {
            value = ((ParsedMax) aggregation).getValue();
        } else if (aggregation instanceof ParsedMin) {
            value = ((ParsedMin) aggregation).getValue();
        } else if (aggregation instanceof ParsedExtendedStats) {
            value = ((ParsedExtendedStats) aggregation).getAvg();
        } else if (aggregation instanceof ParsedStats) {
            value = ((ParsedStats) aggregation).getAvg();
        } else {
            log.error("没有匹配的[{}]", aggregation.getClass());
        }
        if (null != value) {
            // Float 类型的对象，Java 会自动将其提升为 Double 类型
            if (value instanceof Double) {
                double doubleValue = value.doubleValue();
                if (Double.isNaN(doubleValue) || Double.isInfinite(doubleValue)) {
                    return value.toString();
                }
            }
            return String.format("%.02f", value.doubleValue());
        }
        return "";
    }

}
