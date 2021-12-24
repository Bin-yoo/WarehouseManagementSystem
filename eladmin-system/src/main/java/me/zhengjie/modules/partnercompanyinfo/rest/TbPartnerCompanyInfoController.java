package me.zhengjie.modules.partnercompanyinfo.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.partnercompanyinfo.service.TbPartnerCompanyInfoService;
import me.zhengjie.modules.partnercompanyinfo.service.dto.TbPartnerCompanyInfoDto;
import me.zhengjie.modules.partnercompanyinfo.service.dto.TbPartnerCompanyInfoQueryParam;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "tb_partner_company_info管理")
@RequestMapping("/api/tbPartnerCompanyInfo")
public class TbPartnerCompanyInfoController {

    private final TbPartnerCompanyInfoService tbPartnerCompanyInfoService;

    @GetMapping
    @Log("查询tb_partner_company_info")
    @ApiOperation("查询tb_partner_company_info")
    @PreAuthorize("@el.check('tbPartnerCompanyInfo:list')")
    public ResponseEntity query(TbPartnerCompanyInfoQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbPartnerCompanyInfoService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_partner_company_info")
    @ApiOperation("新增tb_partner_company_info")
    @PreAuthorize("@el.check('tbPartnerCompanyInfo:add')")
    public ResponseEntity create(@Validated @RequestBody TbPartnerCompanyInfoDto resources) throws UnsupportedEncodingException {
        return new ResponseEntity<>(tbPartnerCompanyInfoService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_partner_company_info")
    @ApiOperation("修改tb_partner_company_info")
    @PreAuthorize("@el.check('tbPartnerCompanyInfo:edit')")
    public ResponseEntity update(@Validated @RequestBody TbPartnerCompanyInfoDto resources){
        tbPartnerCompanyInfoService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_partner_company_info")
    @ApiOperation("删除tb_partner_company_info")
    @PreAuthorize("@el.check('tbPartnerCompanyInfo:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbPartnerCompanyInfoService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getCompanyCode")
    @Log("获取新的公司编码")
    @ApiOperation("获取新的公司编码")
    @PreAuthorize("@el.check('tbPartnerCompanyInfo:add', 'tbPartnerCompanyInfo:edit')")
    public ResponseEntity getCompanyCode(){
        return new ResponseEntity<>(tbPartnerCompanyInfoService.getCompanyCode(),HttpStatus.OK);
    }

    /*
    @Log("导出tb_partner_company_info")
    @ApiOperation("导出tb_partner_company_info")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbPartnerCompanyInfo:list')")
    public void download(HttpServletResponse response, TbPartnerCompanyInfoQueryParam query) throws IOException {
        tbPartnerCompanyInfoService.download(tbPartnerCompanyInfoService.queryAll(query), response);
    }*/

}
