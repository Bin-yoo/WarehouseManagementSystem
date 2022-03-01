package me.zhengjie.modules.dataImport.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.dataImport.service.DataImportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author LiangBin
* @date 2021-12-08
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "数据导入")
@RequestMapping("/api/dataImport")
public class DataImportController {

    private final DataImportService dataImportService;

    @GetMapping("/getGoodsImportTemplate")
    @Log("下载货品资料导入模板")
    @ApiOperation("下载货品资料导入模板")
    @PreAuthorize("@el.check('dataImport:goodsTemplate')")
    public ResponseEntity getGoodsImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        dataImportService.getGoodsImportTemplate(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getCompanyImportTemplate")
    @Log("下载往来单位资料导入模板")
    @ApiOperation("下载往来单位资料导入模板")
    @PreAuthorize("@el.check('dataImport:companyTemplate')")
    public ResponseEntity getCompanyImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        dataImportService.getCompanyImportTemplate(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getEmployeeImportTemplate")
    @Log("下载员工资料导入模板")
    @ApiOperation("下载员工资料导入模板")
    @PreAuthorize("@el.check('dataImport:employeeTemplate')")
    public ResponseEntity getEmployeeImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        dataImportService.getEmployeeImportTemplate(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getInitialGoodsImportTemplate")
    @Log("下载库初信息导入模板")
    @ApiOperation("下载库初信息导入模板")
    @PreAuthorize("@el.check('dataImport:initialGoodsTemplate')")
    public ResponseEntity getInitialGoodsImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        dataImportService.getInitialGoodsImportTemplate(request, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/goodsImport")
    @Log("货品资料导入")
    @ApiOperation("货品资料导入")
    @PreAuthorize("@el.check('dataImport:goodsImport')")
    public ResponseEntity goodsImport(@RequestParam MultipartFile file) throws IOException {
        Assert.notNull(file, "请上传文件!");
        dataImportService.goodsImport(file);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    @PostMapping("/companiesImport")
    @Log("往来单位资料导入")
    @ApiOperation("往来单位资料导入")
    @PreAuthorize("@el.check('dataImport:companiesImport')")
    public ResponseEntity companiesImport(@RequestParam MultipartFile file) throws IOException {
        Assert.notNull(file, "请上传文件!");
        dataImportService.companiesImport(file);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    @PostMapping("/employeeImport")
    @Log("员工资料导入")
    @ApiOperation("员工资料导入")
    @PreAuthorize("@el.check('dataImport:employeeImport')")
    public ResponseEntity employeeImport(@RequestParam MultipartFile file) throws IOException {
        Assert.notNull(file, "请上传文件!");
        dataImportService.employeeImport(file);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }
}
