package net.gaox.minio.util;

import io.minio.*;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.gaox.minio.config.MinioProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.LongFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p> minio工具类 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 23:57
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MinioUtils {
    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    /**
     * 判断桶是否存在
     *
     * @param bucketName bucket名称
     * @return true存在，false不存在
     */
    public boolean bucketExists(String bucketName) {
        try {
            return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw new RuntimeException("检查桶是否存在失败!", e);
        }
    }

    /**
     * 创建bucket
     *
     * @param bucketName bucket名称
     */
    public boolean createBucket(String bucketName) {
        if (!bucketExists(bucketName)) {
            try {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            } catch (Exception e) {
                log.warn("创建桶失败!", e);
                return false;
            }
        }
        return true;
    }

    /**
     * 根据bucketName获取信息
     *
     * @param bucketName bucket名称
     * @return 单个桶信息
     */
    public Optional<Bucket> getBucket(String bucketName) {
        try {
            return minioClient.listBuckets().stream().filter(b -> b.name().equals(bucketName)).findFirst();
        } catch (Exception e) {
            throw new RuntimeException("根据存储桶名称获取信息失败!", e);
        }
    }

    /**
     * 获取全部bucket
     *
     * @return 所有桶信息
     */
    public List<Bucket> getAllBuckets() {
        try {
            return minioClient.listBuckets();
        } catch (Exception e) {
            throw new RuntimeException("获取全部存储桶失败!", e);
        }
    }

    /**
     * 根据bucketName删除信息
     *
     * @param bucketName bucket名称
     */
    public boolean removeBucket(String bucketName) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            log.warn("根据存储桶名称删除桶失败!", e);
            return false;
        }
        return true;
    }

    /**
     * 判断文件是否存在
     *
     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
     * @return true存在, 反之
     */
    public boolean checkFileIsExist(String objectName) {
        return checkFileIsExist(minioProperties.getBucket(), objectName);
    }

    /**
     * 判断文件是否存在
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
     * @return true存在, 反之
     */
    public boolean checkFileIsExist(String bucketName, String objectName) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 根据文件全路径获取文件流
     *
     * @param objectName 文件名称
     * @return 文件流
     */
    public InputStream getObject(String objectName) {
        return getObject(minioProperties.getBucket(), objectName);
    }

    /**
     * 根据文件桶和文件全路径获取文件流
     *
     * @param bucketName 桶名称
     * @param objectName 文件名
     * @return 文件流
     */
    public InputStream getObject(String bucketName, String objectName) {
        try {
            GetObjectArgs getObjectArgs = GetObjectArgs.builder().bucket(bucketName).object(objectName).build();
            return minioClient.getObject(getObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException("根据文件名获取流失败!", e);
        }
    }

    /**
     * 根据url获取文件流
     *
     * @param url 文件URL
     * @return 文件流
     */
    public InputStream getObjectByUrl(String url) {
        try {
            return new URL(url).openStream();
        } catch (IOException e) {
            throw new RuntimeException("根据URL获取流失败!", e);
        }
    }

    /**
     * 获取文件信息, 如果抛出异常则说明文件不存在
     * https://docs.minio.io/cn/java-client-api-reference.html#statObject
     *
     * @param objectName 文件名称
     * @throws Exception
     */
    public StatObjectResponse getObjectInfo(String objectName) throws Exception {
        return getObjectInfo(minioProperties.getBucket(), objectName);
    }


    /**
     * 获取文件信息, 如果抛出异常则说明文件不存在
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @throws Exception
     */
    public StatObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception {
        StatObjectArgs statObjectArgs = StatObjectArgs.builder().bucket(bucketName).object(objectName).build();
        return minioClient.statObject(statObjectArgs);
    }

    /**
     * 获取文件外链
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     * @param expires    过期时间 <=7
     * @return url
     */
    @SneakyThrows
    public String getObjectURL(String bucketName, String objectName, long expires) {
        // @todo 待验证
        LongFunction<Long> integerIntFunction = i -> Math.min(i, TimeUnit.DAYS.toSeconds(7));
        GetPresignedObjectUrlArgs signedObjectUrlArgs = GetPresignedObjectUrlArgs.builder()
                .bucket(bucketName).object(objectName)
                .expiry(integerIntFunction.apply(expires).intValue()).build();
        String signedObjectUrl = minioClient.getPresignedObjectUrl(signedObjectUrlArgs);
        return signedObjectUrl;
    }

    /**
     * 根据文件前置查询文件
     *
     * @param bucketName bucket名称
     * @param prefix     前缀
     * @param recursive  是否递归查询
     * @return MinioItem 列表
     */
    @SneakyThrows
    public List<Item> getAllObjectsByPrefix(String bucketName, String prefix, boolean recursive) {
        List<Item> list = new ArrayList<>();
        ListObjectsArgs listObjectsArgs = ListObjectsArgs.builder()
                .bucket(bucketName).prefix(prefix)
                .recursive(recursive).build();
        Iterable<Result<Item>> objectsIterator = minioClient.listObjects(listObjectsArgs);
        if (objectsIterator != null) {
            for (Result<Item> result : objectsIterator) {
                Item item = result.get();
                list.add(item);
            }
        }
        return list;
    }

    /**
     * 删除文件
     *
     * @param objectName 文件名称
     */
    public boolean removeObject(String objectName) {
        return removeObject(minioProperties.getBucket(), objectName);
    }

    /**
     * 删除文件
     *
     * @param bucketName bucket名称
     * @param objectName 文件名称
     */
    public boolean removeObject(String bucketName, String objectName) {
        try {
            RemoveObjectArgs removeObjectArgs = RemoveObjectArgs.builder()
                    .bucket(bucketName).object(objectName).build();
            minioClient.removeObject(removeObjectArgs);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 判断文件夹是否存在
     *
     * @param folderName 文件夹名称
     * @return true存在, 反之
     */
    public boolean checkFolderIsExist(String folderName) {
        return checkFolderIsExist(minioProperties.getBucket(), folderName);
    }

    /**
     * 判断文件夹是否存在
     *
     * @param bucketName 桶名称
     * @param folderName 文件夹名称
     * @return true存在, 反之
     */
    public boolean checkFolderIsExist(String bucketName, String folderName) {
        try {
            Iterable<Result<Item>> results = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(bucketName)
                            .prefix(folderName)
                            .recursive(false)
                            .build());
            for (Result<Item> result : results) {
                Item item = result.get();
                if (item.isDir() && folderName.equals(item.objectName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 上传MultipartFile文件到全局默认文件桶中
     *
     * @param file 文件
     * @return 文件对应的URL
     */
    public String putObject(MultipartFile file) {
        return putObject(minioProperties.getBucket(), file.getOriginalFilename(), file);
    }

    /**
     * 上传MultipartFile文件到全局默认文件桶下的folder文件夹下
     *
     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
     * @param file       文件
     * @return 文件对应的URL
     */
    public String putObject(String objectName, MultipartFile file) {
        return putObject(minioProperties.getBucket(), objectName, file);
    }

    /**
     * 上传MultipartFile文件到指定的文件桶下
     *
     * @param bucketName 桶名称
     * @param objectName 文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
     * @param file       文件
     * @return 文件对应的URL
     */
    public String putObject(String bucketName, String objectName, MultipartFile file) {
        // 先创建桶
        createBucket(bucketName);
        String fileName = getAvailableObjectName(bucketName, objectName, false);
        // 开始上传
        putMultipartFile(bucketName, fileName, file);
        return fileName;
    }

    /**
     * 上传MultipartFile通用方法
     *
     * @param bucketName 桶名称
     * @param objectName 文件名
     * @param file       文件
     */
    private void putMultipartFile(String bucketName, String objectName, MultipartFile file) {
        InputStream stream;
        try {
            stream = file.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("文件流获取错误", e);
        }
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName).object(objectName).contentType(file.getContentType())
                    .stream(stream, stream.available(), -1).build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException("文件流上传错误", e);
        }
    }

    /**
     * 上传文件
     * 文件类型 image/jpeg: jpg图片格式
     * 详细可看: https://www.runoob.com/http/http-content-type.html
     *
     * @param objectName  文件名
     * @param stream      文件流
     * @param contentType 文件类型
     * @return 文件url
     */
    public String putObject(String objectName, InputStream stream, String contentType) {
        return putObject(minioProperties.getBucket(), objectName, stream, contentType);
    }

    /**
     * 上传流到指定的文件桶下
     *
     * @param bucketName  桶名称
     * @param objectName  文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
     * @param stream      文件流
     * @param contentType 文件类型
     * @return 文件对应的URL
     */
    public String putObject(String bucketName, String objectName, InputStream stream, String contentType) {
        // 先创建桶
        createBucket(bucketName);
        String fileName = getAvailableObjectName(bucketName, objectName, false);
        // 开始上传
        putInputStream(bucketName, fileName, stream, contentType);
        return fileName;
    }

    /**
     * 上传InputStream通用方法
     *
     * @param bucketName 桶名称
     * @param objectName 文件名
     * @param stream     文件流
     */
    private void putInputStream(String bucketName, String objectName, InputStream stream, String contentType) {
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName).object(objectName).contentType(contentType)
                    .stream(stream, stream.available(), -1).build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException("文件流上传错误", e);
        }
    }

    /**
     * 上传bytes文件
     *
     * @param objectName  文件名
     * @param bytes       字节
     * @param contentType 文件类型
     * @return 文件url
     */
    public String putObject(String objectName, byte[] bytes, String contentType) {
        return putObject(minioProperties.getBucket(), objectName, bytes, contentType);
    }

    /**
     * 上传流到指定的文件桶下
     *
     * @param bucketName  桶名称
     * @param objectName  文件名称, 如果要带文件夹请用 / 分割, 例如 /help/index.html
     * @param bytes       字节
     * @param contentType 文件类型
     * @return 文件对应的URL
     */
    public String putObject(String bucketName, String objectName, byte[] bytes, String contentType) {
        // 先创建桶
        createBucket(bucketName);
        String fileName = getAvailableObjectName(bucketName, objectName, false);
        // 开始上传
        putBytes(bucketName, fileName, bytes, contentType);
        return fileName;
    }

    /**
     * 上传 bytes 通用方法
     *
     * @param bucketName 桶名称
     * @param objectName 文件名
     * @param bytes      字节编码
     */
    private void putBytes(String bucketName, String objectName, byte[] bytes, String contentType) {
        // 字节转文件流
        InputStream stream = new ByteArrayInputStream(bytes);
        try {
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName).object(objectName).contentType(contentType)
                    .stream(stream, stream.available(), -1).build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException("文件流上传错误", e);
        }
    }

    /**
     * 上传File文件到默认桶下
     *
     * @param objectName  文件名
     * @param file        文件
     * @param contentType 文件类型
     * @return 文件对应的URL
     */
    public String putObject(String objectName, File file, String contentType) {
        return putObject(minioProperties.getBucket(), objectName, file, contentType);
    }

    /**
     * 上传File文件
     *
     * @param bucketName  文件桶
     * @param objectName  文件名
     * @param file        文件
     * @param contentType 文件类型
     * @return 文件对应的URL
     */
    public String putObject(String bucketName, String objectName, File file, String contentType) {
        // 先创建桶
        createBucket(bucketName);
        String fileName = getAvailableObjectName(bucketName, objectName, false);
        // 开始上传
        putFile(bucketName, fileName, file, contentType);
        return fileName;
    }

    /**
     * 上传 file 通用方法
     *
     * @param bucketName  桶名称
     * @param objectName  文件名
     * @param file        文件
     * @param contentType 文件类型
     */
    private void putFile(String bucketName, String objectName, File file, String contentType) {
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(bucketName).object(objectName).contentType(contentType)
                    .stream(fileInputStream, fileInputStream.available(), -1).build();
            minioClient.putObject(putObjectArgs);
        } catch (Exception e) {
            throw new RuntimeException("文件上传错误", e);
        }
    }

    public String getAvailableObjectName(String bucketName, @NotNull String baseObjectName, boolean fileSuffix) {
        // 使用默认bucket名称如果传入的为空
        bucketName = Optional.ofNullable(bucketName).orElse(minioProperties.getBucket());
        String newObjectName = baseObjectName;
        String fileName = baseObjectName.substring(0, baseObjectName.indexOf("."));
        String postfix = baseObjectName.substring(baseObjectName.indexOf("."));
        // 获取所有符合前缀的对象
        List<Item> items = getAllObjectsByPrefix(bucketName, fileName, fileSuffix);

        if (!CollectionUtils.isEmpty(items)) {
            // 定义一个正则表达式来匹配数字序号
            Pattern pattern = Pattern.compile(fileName + "_([0-9]{1,3})\\..*");
            // 查找当前最大序号（如果有）
            Integer maxNumber = items.stream()
                    .map(Item::objectName)
                    .map(pattern::matcher)
                    // 确保对象名符合预期格式
                    .filter(Matcher::matches)
                    // 提取数字序号部分
                    .map(m -> m.group(1))
                    .map(Integer::parseInt)
                    .max(Integer::compareTo).orElse(0);
            int nextNumber = maxNumber + 1;
            // 文件名 + _序号 + 后缀
            newObjectName = fileName + "_" + nextNumber + postfix;
        }

        return newObjectName;
    }

}
