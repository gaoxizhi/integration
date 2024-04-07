package net.gaox.excel.controller;

import lombok.RequiredArgsConstructor;
import net.gaox.excel.service.ExportService;
import net.gaox.excel.service.ImportService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * <p> 大表上传下载 </p>
 *
 * @author gaox·Eric
 * @date 2019-03-16 20:20:00
 */
@RestController
@RequestMapping("/bigTable")
@RequiredArgsConstructor
public class BigTableController {

    private final ExportService exportService;

    private final ImportService importService;

    @GetMapping("/exportExcelByMock")
    public void exportExcel(@RequestParam(required = false) Integer size, HttpServletResponse response) throws IOException {
        exportService.exportExcelByMock(size, response);
    }

    @GetMapping("/exportBasicExcel")
    public void exportBasicExcel(HttpServletResponse response) throws IOException {
        exportService.exportBasicExcel(response);
    }

    @GetMapping("/exportSheetExcel")
    public void exportSheetExcel(HttpServletResponse response) throws IOException {
        exportService.exportSheetExcel(response);
    }

    @PostMapping("/import")
    public void importExcel(MultipartFile file) throws IOException {
        importService.importExcel(file);
    }

    @PostMapping("/importAsync")
    public void importExcelAsync(MultipartFile file) {
        importService.importExcelAsync(file);
    }

}
