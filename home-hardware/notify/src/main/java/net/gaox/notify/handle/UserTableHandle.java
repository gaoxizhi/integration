package net.gaox.notify.handle;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.domain.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;

import java.io.IOException;

/**
 * <p> canal 从binlog解析对象，同步到redis和es </p>
 *
 * @author gaox·Eric
 * @date 2024-04-12 16:08
 */
@Slf4j
@Component
@CanalTable("user")
@RequiredArgsConstructor
public class UserTableHandle implements EntryHandler<User> {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RestHighLevelClient esClient;

    private final static String INDEX_NAME = "user";

    /**
     * 新增用户记录
     *
     * @param user 用户
     */
    @Override
    public void insert(User user) {
        log.info("新增用户：{}", user);
        redisTemplate.opsForValue().set(String.format("user:%s", user.getName()), user.getId().toString());

        // 保存到es中
        IndexRequest request = new IndexRequest(INDEX_NAME)
                .id(user.getId().toString()).timeout("1s");
        request.source(JSONObject.toJSONString(user), XContentType.JSON);

        // 客户端发送请求，获取响应结果
        try {
            IndexResponse indexResponse = esClient.index(request, RequestOptions.DEFAULT);
            log.info("用户[id={}]添加{}, response={}", user.getId(),
                    RestStatus.OK.equals(indexResponse.status()) ? "成功" : "失败", indexResponse);
        } catch (IOException e) {
            log.error("新增用户记录失败", e);
        }

    }

    @Override
    public void update(User before, User after) {
        log.info("更新前用户：{}", before);
        log.info("更新用户：{}", after);
        String beforeName = before.getName();
        if (StringUtils.isNotBlank(beforeName)) {
            redisTemplate.delete(String.format("user:%s", beforeName));
        }
        redisTemplate.opsForValue().set(String.format("user:%s", after.getName()), after.getId().toString());

        String id = after.getId().toString();
        String source = JSONObject.toJSONString(after);
        try {
            GetRequest getRequest = new GetRequest(INDEX_NAME, id);
            GetResponse getResponse = esClient.get(getRequest, RequestOptions.DEFAULT);
            if (!getResponse.isExists()) {
                IndexRequest request = new IndexRequest(INDEX_NAME).id(id).timeout("1s");
                request.source(source, XContentType.JSON);
                IndexResponse indexResponse = esClient.index(request, RequestOptions.DEFAULT);
                log.info("用户[id={}]添加{}, response={}", id,
                        RestStatus.OK.equals(indexResponse.status()) ? "成功" : "失败", indexResponse);
            } else {
                UpdateRequest request = new UpdateRequest(INDEX_NAME, id);
                request.doc(source, XContentType.JSON);
                UpdateResponse updateResponse = esClient.update(request, RequestOptions.DEFAULT);
                log.info("用户id={}, {}", id, RestStatus.OK.equals(updateResponse.status()) ? "已更新" : "更新失败");
            }
        } catch (IOException e) {
            log.error("更新用户记录失败", e);

        }

    }

    @Override
    public void delete(User user) {
        log.info("删除用户：{}", user);
        redisTemplate.delete(String.format("user:%s", user.getName()));

        String id = user.getId().toString();
        DeleteRequest request = new DeleteRequest(INDEX_NAME, id);

        try {
            DeleteResponse updateResponse = esClient.delete(request, RequestOptions.DEFAULT);
            // 删除成功返回OK，没有找到返回NOT_FOUND
            RestStatus status = updateResponse.status();
            log.info("用户id={}, {}", id, RestStatus.OK.equals(status) ? "已删除" :
                    RestStatus.NOT_FOUND.equals(status) ? "未找到" : "删除失败");
        } catch (IOException e) {
            log.error("删除用户记录失败", e);
        }
    }

}
