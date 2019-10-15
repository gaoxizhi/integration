package net.gaox.aliyunoss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import net.gaox.aliyunoss.config.ParamConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author gaox·Eric
 * @date 2019/3/28 19:22
 */
public class OSSStreamDownload {
    public static String endpoint = ParamConfig.endpoint;
    public static String accessKeyId = ParamConfig.accessKeyId;
    public static String accessKeySecret = ParamConfig.accessKeySecret;
    public static String bucketName = ParamConfig.bucketName;
    public static String folder = ParamConfig.folder;

    public static OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);


    // 创建OSSClient实例。    已过时
    {
//        client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }
    /**
     * <p> 获得文件 </p>
     *
     * @param:
     * @return:
     * @author gaox·Eric
     * @date ${DATE} ${HOUR}:${MINUTE}
     */
    public static void getFiles(String fileName) throws IOException {

        OSSObject ossObject = client.getObject(bucketName, fileName);
        InputStream inputStream = ossObject.getObjectContent();

        for (String v : ossObject.getObjectMetadata().getUserMetadata().values()) {
            System.out.println(v);
        }
        writeToLocal("I://" + fileName, inputStream);
        ossObject.close();
    }
    /**
     * 将InputStream写入本地文件
     *
     * @param destination 写入本地目录
     * @param input       输入流
     * @throws IOException IOException
     */
    public static void writeToLocal(String destination, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[102400];
        File file = new File(destination);
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        input.close();
        downloadFile.close();

    }
    public static void main(String[] args) throws IOException {
        getFiles("mmexport1544249585987.jpg");
    }
}