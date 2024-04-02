package net.gaox.minio.util;

import io.minio.DownloadObjectArgs;
import io.minio.MinioClient;

/**
 * <p> 下载测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 18:26
 */
public class DownLoadDemo {
    public static void main(String[] args) {
        // Create a minioClient with the MinIO server playground, its access key and secret key
        MinioClient minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("admin", "minioadmin")
                .build();

        // Download object given the bucket, object name and output file name
        try {
            String bucketName = "gaox";
            minioClient.downloadObject(
                    DownloadObjectArgs.builder()
                            .bucket(bucketName)
                            .object("gaox.sql")
                            .filename("gaox.sql")
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
