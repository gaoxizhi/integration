package net.gaox.minio.util;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import io.minio.errors.MinioException;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <p> 上传测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 18:18
 */
public class FileUploadTest {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            // Create a minioClient with the MinIO server playground, its access key and secret key
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")
                    .credentials("admin", "minioadmin")
                    .build();
            // 创建bucket
            String bucketName = "gaox";
            boolean exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!exists) {
                // 不存在，创建bucket
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            // 上传文件
            File file = new File("/Users/gaox/Downloads/gaox.sql");
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(bucketName)
                    .object(file.getName())
                    .filename(file.getPath())
                    .build();
            minioClient.uploadObject(uploadObjectArgs);
            System.out.println("上传文件成功");
        } catch (MinioException e) {
            System.out.println("Error occurred: " + e);
            System.out.println("HTTP trace: " + e.httpTrace());
        }
    }

}