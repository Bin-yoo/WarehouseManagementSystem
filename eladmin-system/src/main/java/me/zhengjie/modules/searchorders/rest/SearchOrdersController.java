package me.zhengjie.modules.searchorders.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.searchorders.service.SearchOrdersService;
import me.zhengjie.modules.searchorders.service.dto.SearchOrdersQueryParam;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
* @author LiangBin
* @date 2022-01-03
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "单据查询管理")
@RequestMapping("/api/searchOrders")
public class SearchOrdersController {

    private final SearchOrdersService searchOrdersService;

    @GetMapping
    @Log("查询单据")
    @ApiOperation("查询单据")
    @PreAuthorize("@el.check('searchOrders:list')")
    public ResponseEntity query(SearchOrdersQueryParam query, Pageable pageable){
        return new ResponseEntity<>(searchOrdersService.queryAll(query,pageable),HttpStatus.OK);
    }

    /*
    @Log("导出单据查询")
    @ApiOperation("导出单据查询")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('searchOrdersService:list')")
    public void download(HttpServletResponse response, TbOrdersQueryParam query) throws IOException {
        searchOrdersServiceService.download(searchOrdersServiceService.queryAll(query), response);
    }*/

    @GetMapping("/getOrderGoodList")
    @Log("获取单据货品列表")
    @ApiOperation("获取单据货品列表")
    @PreAuthorize("@el.check('searchOrders:detail')")
    public ResponseEntity getOrderGoodList(String id){
        return new ResponseEntity<>(searchOrdersService.getOrderGoodList(id),HttpStatus.OK);
    }

    @GetMapping("/printOrderReport")
    @Log("打印单据")
    @ApiOperation("打印单据")
    @PreAuthorize("@el.check('searchOrders:print')")
    public ResponseEntity printOrderReport(String id, HttpServletResponse response) throws Exception {
        searchOrdersService.printOrderReport(id, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrderPrintingInfo")
    @Log("获取打印单据信息")
    @ApiOperation("获取打印单据信息")
    @PreAuthorize("@el.check('searchOrders:print')")
    public ResponseEntity getOrderPrintingInfo(String id) throws Exception {
        return new ResponseEntity<>(searchOrdersService.getOrderPrintingInfo(id), HttpStatus.OK);
    }
}
