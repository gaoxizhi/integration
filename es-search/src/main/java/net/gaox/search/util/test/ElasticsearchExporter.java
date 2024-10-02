package net.gaox.search.util.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.*;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * <p> es 数据迁移 </p>
 *
 * @author gaox·Eric
 * @date 2024-06-24 15:15
 */
@Slf4j
public class ElasticsearchExporter {

    private static RestHighLevelClient client =
            new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.0.166", 9200, "http")));

    private static final String INDEX_NAME = "product_v2";
    private static final String FILE_PATH = "file/a/";
    // 最大文件大小为 100MB
    private static final long MAX_FILE_SIZE_BYTES = 100 * 1024 * 1024;

    public static void main(String[] args) {

        try {
            // 创建滚动请求
            SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
            // 设置滚动时间
            searchRequest.scroll(TimeValue.timeValueMinutes(1L));

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            // 查询所有文档
            searchSourceBuilder.query(QueryBuilders.matchAllQuery());
            // 设置每次滚动查询返回的文档数量为 1000
            searchSourceBuilder.size(1000);
            // searchSourceBuilder.sort(new FieldSortBuilder("id").order(SortOrder.ASC));


            searchRequest.source(searchSourceBuilder);

            SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

            String scrollId = searchResponse.getScrollId();
            SearchHit[] searchHits = searchResponse.getHits().getHits();

            int fileCounter = 1;
            long currentFileSize = 0;
            Path currentFilePath = Paths.get(FILE_PATH, getName(fileCounter));
            BufferedWriter writer = Files.newBufferedWriter(currentFilePath);

            try {
                while (searchHits != null && searchHits.length > 0) {
                    for (SearchHit hit : searchHits) {
                        // 获取文档 ID
                        String docId = hit.getId();

                        // 获取源数据
                        String source = hit.getSourceAsString();

                        // 构建 Bulk API 格式的操作
                        String indexOperation = "{ \"index\" : { \"_index\" : \"" + INDEX_NAME + "\", \"_id\" : \"" + docId + "\" } }";

                        // 计算当前文件大小
                        currentFileSize += indexOperation.getBytes().length + source.getBytes().length;

                        // 如果文件大小超过阈值，创建新文件
                        if (currentFileSize > MAX_FILE_SIZE_BYTES) {
                            // 关闭当前文件写入流
                            closeWriter(writer);
                            fileCounter++;
                            currentFileSize = 0;
                            currentFilePath = Paths.get(FILE_PATH, getName(fileCounter));
                            // 创建新的文件写入流
                            writer = Files.newBufferedWriter(currentFilePath);
                        }

                        writer.write(indexOperation);
                        writer.newLine();
                        writer.write(source);
                        writer.newLine();
                    }

                    // 继续滚动查询
                    SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
                    scrollRequest.scroll(TimeValue.timeValueMinutes(1L));

                    searchResponse = client.scroll(scrollRequest, RequestOptions.DEFAULT);
                    scrollId = searchResponse.getScrollId();
                    searchHits = searchResponse.getHits().getHits();
                }
            } catch (IOException e) {
                log.warn("have exception: ", e);
            } finally {
                closeWriter(writer);
                try {
                    // 清除滚动上下文
                    ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
                    clearScrollRequest.addScrollId(scrollId);
                    client.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
                    client.close();
                } catch (IOException e) {
                    log.warn("have exception: ", e);
                }
            }
        } catch (Exception e) {
            log.warn("have exception: ", e);
        }
    }

    private static String getName(int fileCounter) {
        return INDEX_NAME + "_data_" + String.format("%03d", fileCounter) + ".json";
    }

    /**
     * 辅助方法：关闭 BufferedWriter
     *
     * @param writer
     */
    private static void closeWriter(BufferedWriter writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                log.warn("have exception: ", e);
            }
        }
    }
}
