package net.gaox.search.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gaox.search.entity.Product;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2024-04-29 19:57
 */
@Service
public class ProductService {

    @Autowired
    private RestHighLevelClient client;

    public List<Product> search(String keyword, String name, Integer minAge, Integer maxAge,
                                String startIpAddress, String endIpAddress,
                                String country, String province, String likeDomain) throws IOException {

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // 全文搜索
        if (StringUtils.hasText(keyword)) {
            // 设置对已经条件检索的，进行移除
            List<String> fieldsToSearch = Arrays.asList("login_log", "browser_record", "cookie_data", "saved_login_info");
            MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(keyword, fieldsToSearch.toArray(new String[0]));
        }
        // Name filtering
        if (StringUtils.hasText(name)) {
            boolQuery.must(QueryBuilders.matchQuery("name", name));
        }

        // Age range filtering
        if (minAge != null && maxAge != null) {
            RangeQueryBuilder ageRange = QueryBuilders.rangeQuery("age").gte(minAge).lte(maxAge);
            boolQuery.must(ageRange);
        }

        // IP address range filtering
        if (StringUtils.hasText(startIpAddress) && StringUtils.hasText(endIpAddress)) {
            RangeQueryBuilder ipRange = QueryBuilders.rangeQuery("ipAddress").from(startIpAddress).to(endIpAddress);
            boolQuery.must(ipRange);
        }

        // Country and Province nested query
        BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
        if (StringUtils.hasText(country)) {
            // 嵌套路径
            // 在嵌套对象内的country字段进行匹配
            nestedBoolQuery.must(QueryBuilders.termQuery("location.country", country));
        }
        if (StringUtils.hasText(province)) {
            nestedBoolQuery.must(QueryBuilders.termQuery("location.province", province));
        }
        // 确切匹配，可以选择不计算分数 ScoreMode.None
        NestedQueryBuilder nestedQuery = QueryBuilders.nestedQuery("location", nestedBoolQuery, ScoreMode.None);
        boolQuery.must(nestedQuery);

        // Domain fuzzy search
        if (StringUtils.hasText(likeDomain)) {
            FuzzyQueryBuilder fuzzyQuery = QueryBuilders.fuzzyQuery("domain", likeDomain);
            boolQuery.should(fuzzyQuery);
        }

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(boolQuery);

        // Sorting by insertion time in descending order
        sourceBuilder.sort(SortBuilders.fieldSort("insertTime").order(SortOrder.DESC));

        // Execute the search
        SearchRequest searchRequest = new SearchRequest("my_index");
        searchRequest.source(sourceBuilder);

        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

        // Convert Hits to your entity list
        SearchHit[] hits = response.getHits().getHits();
        List<Product> entities = new ArrayList<>();
        for (SearchHit hit : hits) {
            Product entity = new ObjectMapper().readValue(hit.getSourceAsString(), Product.class);
            entities.add(entity);
        }

        return entities;
    }

}