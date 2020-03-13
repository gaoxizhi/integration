package net.gaox.fileserver.controller;

import net.gaox.fileserver.util.FileUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>  </p>
 *
 * @author gaox·Eric
 * @date 2019/8/4 20:05
 */
@RestController
@RequestMapping("/file")
public class FileController {

    private FileUtil fileUtil = new FileUtil();

    @PostMapping()
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        Boolean uploadFile = fileUtil.uploadFile(file);
        return uploadFile ? "上传成功！" : "上传失败！";
    }
}