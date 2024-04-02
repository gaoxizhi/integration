package net.gaox.search.util.test.batch;

import lombok.extern.slf4j.Slf4j;
import net.gaox.search.constant.Constants;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/**
 * <p> 查询测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-21 19:51
 */
@Slf4j
public class TestESQuery {

    private static RestHighLevelClient client =
            new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));

    public static void main(String[] args) throws Exception {
        String keyword = "时尚连衣裙";
        int start = 0;
        int count = 10;

        SearchHits hits = search(keyword, start, count);

        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits) {
            log.info("id={}, score={}, SourceString={}", hit.getId(), hit.getScore(), hit.getSourceAsString());
        }
        client.close();
    }

    private static SearchHits search(String keyword, int start, int count) throws IOException {
        SearchRequest searchRequest = new SearchRequest(Constants.INDEX_NAME);

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //关键字匹配
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("name", keyword);
        //模糊匹配
        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
        sourceBuilder.query(matchQueryBuilder);
        sourceBuilder.from(start);//第几页
        sourceBuilder.size(count); //第几条
        //匹配 分值 度从高到低
        sourceBuilder.sort(SortBuilders.scoreSort().order(SortOrder.DESC));

        //指定字段排序
        sourceBuilder.sort("price", SortOrder.DESC);

        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        return hits;
    }

}
