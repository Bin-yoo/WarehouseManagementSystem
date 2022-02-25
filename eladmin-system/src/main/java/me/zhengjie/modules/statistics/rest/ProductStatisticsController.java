package me.zhengjie.modules.statistics.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.statistics.service.ProductStatisticsService;
import me.zhengjie.modules.statistics.service.dto.ProductStatisticsQueryParam;
import me.zhengjie.modules.statistics.service.dto.PurchaseStatisticsQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author LiangBin
* @date 2022-01-03
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "货品生产汇总")
@RequestMapping("/api/productStatistics")
public class ProductStatisticsController {

    private final ProductStatisticsService productStatisticsService;

    @GetMapping
    @Log("查询货品生产汇总列表")
    @ApiOperation("查询货品生产汇总列表")
    @PreAuthorize("@el.check('productStatistics:list')")
    public ResponseEntity query(ProductStatisticsQueryParam query, Pageable pageable){
        return new ResponseEntity<>(productStatisticsService.queryAll(query, pageable),HttpStatus.OK);
    }

    @GetMapping("/getDetail")
    @Log("获取货品生产汇总详情")
    @ApiOperation("获取货品生产汇总详情")
    @PreAuthorize("@el.check('productStatistics:list')")
    public ResponseEntity getDetail(Long goodId, Pageable pageable){
        if (goodId == null) {
            throw new BadRequestException("缺少货品id!");
        }
        return new ResponseEntity<>(productStatisticsService.getDetail(goodId, pageable),HttpStatus.OK);
    }

    /*
    @Log("导出单据")
    @ApiOperation("导出单据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbOrders:list')")
    public void download(HttpServletResponse response, TbOrdersQueryParam query) throws IOException {
        productStatisticsService.download(productStatisticsService.queryAll(query), response);
    }*/

}
