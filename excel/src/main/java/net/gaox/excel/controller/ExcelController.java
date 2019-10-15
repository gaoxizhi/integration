package net.gaox.excel.controller;

import net.gaox.excel.service.ExcelService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;


/**
 * <p> controller </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */
@Controller
public class ExcelController {


    private final ExcelService importService;

    public ExcelController(ExcelService importService) {
        this.importService = importService;
    }

    /**
     * 表单方式提交Excel文件并解析
     *
     * @param request 请求，表单携带file对象的文件
     * @return 解析结果
     * @throws IOException      IO异常
     * @throws ServletException 程序控制起异常
     */
    @PostMapping(value = "/upload")
    @ResponseBody
    public String uploadExcel(HttpServletRequest request) throws IOException, ServletException {
        //获得表单提交信息集合
        Collection<Part> parts = request.getParts();
        for (Part part : parts) {
            System.out.println("-----类型名称------->" + part.getName());
            System.out.println("-----类型------->" + part.getContentType());
            System.out.println("-----提交的类型名称------->" + part.getSubmittedFileName());
            System.out.println("----流-------->" + part.getInputStream());
            System.out.println("----文件流名称-------->" + part.getSubmittedFileName());
            System.out.println("----长度-------->" + part.getSize());
        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file");
        if (null == file) {
            return "文件上传失败！";
        }
        if (file.isEmpty()) {
            return "文件不能为空";
        }
        InputStream inputStream = file.getInputStream();
        List list = importService.getBankListByExcel(inputStream, file.getOriginalFilename());
        inputStream.close();
        if (null == list) {
            System.err.println("列表为空！");
        }
        printData(list);
        System.out.println("======================================================");
        printDataLine(list);
        return "上传成功";
    }

    public void printData(List list) {
        if (0 < list.size()) {
            for (Object o : list) {
                if (o instanceof List) {
                    printData((List) o);
                } else {
                    System.out.println(o.toString());
                }
            }
        }
    }

    public void printDataLine(List list) {
        if (0 < list.size()) {
            for (Object o : list) {
                if (o instanceof List && 0 < ((List) o).size() && !(((List) o).get(0) instanceof List)) {
                    final StringBuilder builder = new StringBuilder("该行数据：[");
                    for (Object ch : (List<Object>) o) {
                        builder.append(ch).append("|");
                    }
                    System.out.println(builder.deleteCharAt(builder.length() - 1).append("]"));
                } else {
                    assert o instanceof List;
                    printDataLine((List) o);
                }
            }
        }
    }

}
