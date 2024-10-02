package net.gaox.search.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchRestClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * <p> ES 客户端配置 </p>
 *
 * @author gaox·Eric
 * @date 2024-02-21 19:32
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class ElasticSearchClientConfig {

    private final ElasticsearchRestClientProperties clientProperties;

    @Bean
    public RestHighLevelClient restHighLevelClient() throws URISyntaxException {
        URI clintUri = new URI(clientProperties.getUris().get(0));
        // new HttpHost("localhost", 9200, "http")));
        HttpHost httpHost = new HttpHost(clintUri.getHost(), clintUri.getPort(), clintUri.getScheme());
        // RestClient.builder 可传入集群地址列表
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(httpHost));
        return client;
    }

}
