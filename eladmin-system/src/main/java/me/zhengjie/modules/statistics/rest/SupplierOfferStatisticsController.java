package me.zhengjie.modules.statistics.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.statistics.service.SupplierOfferStatisticsService;
import me.zhengjie.modules.statistics.service.dto.PurchaseStatisticsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
* @author LiangBin
* @date 2022-01-03
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "供应商供货汇总")
@RequestMapping("/api/supplierOfferStatistics")
public class SupplierOfferStatisticsController {

    private final SupplierOfferStatisticsService supplierOfferStatisticsService;

    @GetMapping
    @Log("查询供应商供货汇总列表")
    @ApiOperation("查询供应商供货汇总列表")
    @PreAuthorize("@el.check('supplierOfferStatistics:list')")
    public ResponseEntity query(PurchaseStatisticsQueryParam query, Pageable pageable){
        return new ResponseEntity<>(supplierOfferStatisticsService.queryAll(query,pageable),HttpStatus.OK);
    }

    @GetMapping("/getDetail")
    @Log("获取供应商供货汇总详情")
    @ApiOperation("获取供应商供货汇总详情")
    @PreAuthorize("@el.check('supplierOfferStatistics:list')")
    public ResponseEntity getDetail(Long sourceId, Pageable pageable){
        if (sourceId == null) {
            throw new BadRequestException("缺少供应商id");
        }
        return new ResponseEntity<>(supplierOfferStatisticsService.getDetail(sourceId, pageable),HttpStatus.OK);
    }

    /*
    @Log("导出单据")
    @ApiOperation("导出单据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbOrders:list')")
    public void download(HttpServletResponse response, TbOrdersQueryParam query) throws IOException {
        supplierOfferStatisticsService.download(supplierOfferStatisticsService.queryAll(query), response);
    }*/

}
