package net.gaox.domain.model.dto;

import lombok.Data;

/**
 * <p> kv 查询参数 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-13 13:26
 */
@Data
public class KvQuery extends PageQuery {
    private String keyLike;
}
