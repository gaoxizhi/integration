package net.gaox.minio.model.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p> 上传文件详情 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-02 00:28
 */
@Data
@Builder
@Accessors(chain = true)
public class FileInfoVO {
    private String bucketName;
    private String originName;
    private String fileName;
    private String fileUrl;
}
