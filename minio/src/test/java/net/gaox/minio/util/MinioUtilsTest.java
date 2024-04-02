package net.gaox.minio.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p> minioUtils 测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 23:58
 */
@Slf4j
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class MinioUtilsTest {

    private static final String bucketName = "gaox";

    @Autowired
    private MinioUtils minioUtils;

    @Test
    void bucketAll() {
        String testBucketName = bucketName + "-test";
        Boolean gaoxExists = minioUtils.bucketExists(testBucketName);
        log.info("bucket [gaox] exists = {}", gaoxExists);
        if (!gaoxExists) {
            boolean createBucket = minioUtils.createBucket(testBucketName);
            log.info("bucket [gaox] create {}", createBucket);
        }
        boolean removeBucket = minioUtils.removeBucket(testBucketName);
        log.info("bucket [gaox] remove {}", removeBucket);
    }

}