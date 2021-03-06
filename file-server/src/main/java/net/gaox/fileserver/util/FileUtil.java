package net.gaox.fileserver.util;

import net.gaox.util.api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/8/4 19:45
 */
//@PropertySource("classpath:file.yml")
@Component
public class FileUtil {

    private final String UP_LOAD_SUCCESS = "上传成功！";
    private final String DELETE_FAILD = "删除失败！";
    @Value("${file-local.base-path}")
    private String parentFile = "D:\\dir\\";

    /**
     * 上传文件
     *
     * @param file
     */
    public Boolean uploadFile(MultipartFile file) {
        try {
            File in = new File(parentFile + file.getOriginalFilename());
            File dest = in.getParentFile();

            //如果这个文件夹不存在，创建文件夹
            if (!dest.exists()) {
                boolean mkdirs = dest.mkdirs();
                if (!mkdirs) {
                    return false;
                }
            }
            // copy 将文件写入
            file.transferTo(in);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void downloadFile(String name, HttpServletResponse response) {
        File file = new File(name);
        if (!file.exists()) {
            throw new ApiException(name + "文件不存在");
        }
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + name);
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            ServletOutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 查找一个目录下所有的文件
     *
     * @param dir 目录
     * @throws IllegalArgumentException 目录不存在，或者不是目录
     */
    public static void findAllFile(File dir) throws IllegalArgumentException {
        if (!dir.exists()) {
            throw new IllegalArgumentException("目录：" + dir + " 不存在");
        }
        if (!dir.isDirectory()) {
            throw new IllegalArgumentException(dir + " 不是目录");
        }
        // 返回的是直接子目录（文件）对象
        File[] files = dir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 递归查询字目录
//                    listDirectory(file);
                } else {
                    System.out.println(file);
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath
     * @param fileName
     * @return
     */
    public Boolean deleteFile(String filePath, String fileName) {
        File file = new File(filePath + fileName);

        // delete file
        boolean delete = file.delete();
        if (!delete) {
            System.out.println(DELETE_FAILD);
        }
        return delete;
    }
}