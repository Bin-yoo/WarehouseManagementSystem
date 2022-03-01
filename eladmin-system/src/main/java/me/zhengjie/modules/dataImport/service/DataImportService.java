package me.zhengjie.modules.dataImport.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.modules.employee.domain.TbEmployee;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
* @author LiangBin
* @date 2021-12-08
*/
public interface DataImportService extends CommonService<TbEmployee>  {

    void getGoodsImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void getCompanyImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void getEmployeeImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void getInitialGoodsImportTemplate(HttpServletRequest request, HttpServletResponse response) throws IOException;

    void goodsImport(MultipartFile file) throws IOException;

    void companiesImport(MultipartFile file) throws IOException;

    void employeeImport(MultipartFile file) throws IOException;
}
