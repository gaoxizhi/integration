package net.gaox.minio.controller;

import com.alibaba.fastjson.JSON;
import io.minio.StatObjectResponse;
import io.minio.messages.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.gaox.minio.config.MinioProperties;
import net.gaox.minio.model.vo.FileInfoVO;
import net.gaox.minio.util.MinioUtils;
import net.gaox.util.api.ApiResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p> minio测试 </p>
 *
 * @author gaox·Eric
 * @date 2024-04-01 23:33
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class MinioController {

    private final MinioProperties minioProperties;
    private final MinioUtils minioUtils;

    @GetMapping("/list")
    public ApiResponse list(@RequestParam(value = "prefix", required = false) String prefix) {
        List<Item> allObjectsByPrefix = minioUtils.getAllObjectsByPrefix(
                minioProperties.getBucket(), prefix, true);
        String format = "{'fileName':'%s','fileSize':'%s'}";

        List<Object> list = allObjectsByPrefix.stream()
                .map(item -> String.format(format, item.objectName(), formatFileSize(item.size())))
                .map(JSON::parse).collect(Collectors.toList());
        return ApiResponse.success(list);
    }

    @PostMapping("/upload")
    public ApiResponse upload(@RequestParam(name = "file", required = false) MultipartFile[] file) {
        if (file == null || file.length == 0) {
            return ApiResponse.fail("上传文件不能为空");
        }
        List<FileInfoVO> list = new ArrayList<>(file.length);

        for (MultipartFile multipartFile : file) {
            String originalFilename = multipartFile.getOriginalFilename();
            String bucket = minioProperties.getBucket();
            FileInfoVO info = FileInfoVO.builder().bucketName(bucket).originName(originalFilename).build();
            try {
                //文件上传
                String fileName = minioUtils.putObject(bucket, originalFilename, multipartFile.getBytes(),
                        multipartFile.getContentType());
                info.setFileName(fileName);
            } catch (Exception e) {
                log.error(e.getMessage());
                return ApiResponse.fail("上传失败");
            }
            list.add(info);
        }
        return ApiResponse.success(list);
    }

    @GetMapping("/download/{fileName}")
    public void download(HttpServletResponse response, @PathVariable("fileName") String fileName) {
        InputStream in = null;
        try {
            StatObjectResponse stat = minioUtils.getObjectInfo(fileName);
            response.setContentType(stat.contentType());
            String encodeName = "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-Disposition", encodeName);
            //文件下载
            in = minioUtils.getObject(fileName);
            IOUtils.copy(in, response.getOutputStream());
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    @DeleteMapping("/delete/{fileName}")
    public ApiResponse delete(@PathVariable("fileName") String fileName) {
        boolean removed = minioUtils.removeObject(fileName);
        if (removed) {
            return ApiResponse.success("删除成功");
        } else {
            return ApiResponse.fail("删除失败");
        }
    }

    /**
     * 格式化文件大小
     *
     * @param size 文件大小（字节）
     * @return 格式化后的文件大小字符串，带单位
     * @throws IllegalArgumentException 如果输入的文件大小为负
     */
    public static String formatFileSize(long size) {
        if (size < 0) {
            throw new IllegalArgumentException("文件大小不能为负数");
        }

        double bytes = size;
        String[] units = new String[]{"B", "KB", "MB", "GB", "TB", "PB"};
        int unitIndex = 0;

        while (bytes >= 1024 && unitIndex < units.length - 1) {
            bytes /= 1024.0;
            unitIndex++;
        }
        if ((bytes - (int) bytes) < 0.0001d) {
            return String.format("%d %s", (int) bytes, units[unitIndex]);
        }
        return String.format("%.2f %s", bytes, units[unitIndex]);
    }

}
