package net.gaox.excel.controller;

import net.gaox.excel.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 * <p> controller </p>
 *
 * @author: gaox·Eric
 * @date: 2019-03-16 20:20:00
 */
@Controller
public class ExcelController {


    @Autowired
    private ExcelService importService;


    @PostMapping(value = "/upload")
    @ResponseBody
    public String uploadExcel(HttpServletRequest request) throws Exception {
        //获得表单提交信息集合
        Collection<Part> parts = request.getParts();
        for (Iterator<Part> iterator = parts.iterator(); iterator.hasNext(); ) {

            Part part = iterator.next();

            System.out.println("-----类型名称------->" + part.getName());

            System.out.println("-----类型------->" + part.getContentType());

            System.out.println("-----提交的类型名称------->" + part.getSubmittedFileName());

            System.out.println("----流-------->" + part.getInputStream());

            System.out.println("----文件流名称-------->" + part.getSubmittedFileName());

            System.out.println("----长度-------->" + part.getSize());

        }
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        MultipartFile file = multipartRequest.getFile("filename");
        if (file.isEmpty()) {
            return "文件不能为空";
        }
        InputStream inputStream = file.getInputStream();
        List<List<Object>> list = importService.getBankListByExcel(inputStream, file.getOriginalFilename());
        inputStream.close();

        for (int i = 0; i < list.size(); i++) {
            List<Object> lo = list.get(i);
            //TODO 随意发挥
            System.out.println(lo);
        }
        return "上传成功";
    }
}
