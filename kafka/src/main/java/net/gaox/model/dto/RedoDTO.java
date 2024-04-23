package net.gaox.model.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p> 重做 dto </p>
 *
 * @author gaox·Eric
 * @date 2024-04-23 11:31
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class RedoDTO {
    private List<String> topics;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
}
