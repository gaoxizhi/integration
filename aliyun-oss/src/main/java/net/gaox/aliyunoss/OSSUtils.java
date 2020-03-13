package net.gaox.aliyunoss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import net.gaox.aliyunoss.config.ParamConfig;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author gaox·Eric
 * @date 2019/3/28 11:11
 * 查看图片示例
 * XXXX.oss-cn-beijing.aliyuncs.com/mmexport1544249585987.jpg
 */
public class OSSUtils {
    public static String endpoint = ParamConfig.endpoint;
    public static String accessKeyId = ParamConfig.accessKeyId;
    public static String accessKeySecret = ParamConfig.accessKeySecret;
    public static String bucketName = ParamConfig.bucketName;
    public static String folder = ParamConfig.folder;
    /**
     * 初始化获得Oss连接对象
     */
    private static OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


    public static void main(String[] args) throws IOException {
        //测试上传文件
        Resource resource = new ClassPathResource("banner.txt");
        File file = resource.getFile();
        final String fileName = "test_file_name.txt";
        if (uploadFile(file, fileName)) {
            System.out.println("ok");
        } else {
            System.out.println("失败");
        }
        //列举文件
//        listFiles();
        //测试获取文件
//        getFile("mmexport1544249585987.jpg","XXXX");
        //测试删除文件
        deleteFile("banner.txt");

    }

    /**
     * 上传文件到阿里云OSS
     *
     * @param file 本地文件对象
     * @param key  oss对于url
     * @return
     */
    public static boolean uploadFile(File file, String key) {
        try {
            client.putObject(new PutObjectRequest(bucketName, key, file));
            client.setObjectAcl(bucketName, key, CannedAccessControlList.PublicRead);
            client.setObjectAcl(bucketName, key, CannedAccessControlList.Default);

            final ObjectMetadata objectMetadata = client.getObjectMetadata(bucketName, key);
            System.out.println(objectMetadata.getCacheControl());
            System.out.println(objectMetadata.getContentDisposition());
            System.out.println(objectMetadata.getContentLength());
            System.out.println(objectMetadata.getContentType());
            System.out.println(objectMetadata.getUserMetadata());
            System.out.println(objectMetadata.getRawExpiresValue());
            System.out.println(objectMetadata.getETag());


            String fileName = "gaox";
            client.putObject(new PutObjectRequest(bucketName, fileName + ".txt", new InputStream() {
                @Override
                public int read() throws IOException {
                    return 0;
                }
            }));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//            // 关闭OSSClient。
//            client.shutdown();
//        }
        return false;
    }

    /**
     * 列举文件
     */
    public static void listFiles() {
        /**
         * 列举文件。
         * 如果不设置KeyPrefix，则列举存储空间下所有的文件。
         * KeyPrefix，则列举包含指定前缀的文件。
         */
        ObjectListing objectListing = client.listObjects(bucketName);
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            System.out.println("\t" + s.getKey());
        }
    }

    /**
     * 删除阿里云OSS上文件
     *
     * @param key
     * @return
     */
    public static boolean deleteFile(String key) {
        try {
            OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            boolean exists = client.doesObjectExist(bucketName, key);
            if (exists) {
                client.deleteObject(bucketName, key);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName 文件名
     * @return 存在否
     */
    public static boolean getFile(String fileName) {
        try {
            OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            final boolean exist = client.doesObjectExist(bucketName, fileName);
            if (exist) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向OSS追加日志输出
     *
     * @param content 日志内容
     * @return 追加状态
     */
    public static boolean writeFile(String content) {
        String logFile = "dir/log.log";

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //判断是否有该文件
        if (!ossClient.doesObjectExist(bucketName, logFile)) {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, logFile, new ByteArrayInputStream("创建追加日志记录文件：\n".getBytes()));
            ossClient.putObject(putObjectRequest);
        }

        // 从OSS取文件，读其大小
        Long position = ossClient.getSimplifiedObjectMeta(bucketName, logFile).getSize();

        // 创建上传Object的Metadata，如不声明则写入OSS中文会乱码
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("text/plain; charset=utf-8");

        // 通过AppendObjectRequest设置多个参数。
        AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, logFile, new ByteArrayInputStream((content + "\n").getBytes()), meta);

        // nextPosition指明下一次请求中应当提供的Position，即文件当前的长度。
        appendObjectRequest.setPosition(position);
        ossClient.appendObject(appendObjectRequest);
        //上次修改时间
        long modTime = ossClient.getSimplifiedObjectMeta(bucketName, logFile).getLastModified().getTime();
        // 关闭OSSClient。
        ossClient.shutdown();

        //是否更新
        return Math.abs(System.currentTimeMillis() - modTime) < 20;
    }

}