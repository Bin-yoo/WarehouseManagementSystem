package me.zhengjie.modules.orders.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.orders.domain.vo.OrderVo;
import me.zhengjie.modules.orders.service.ProductOrdersService;
import me.zhengjie.modules.orders.service.dto.TbOrdersQueryParam;
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
@Api(tags = "生产入库单据管理")
@RequestMapping("/api/productOrders")
public class ProductOrdersController {

    private final ProductOrdersService productOrdersService;

    @GetMapping
    @Log("查询生产入库单据")
    @ApiOperation("查询生产入库单据")
    @PreAuthorize("@el.check('productOrders:list')")
    public ResponseEntity query(TbOrdersQueryParam query, Pageable pageable){
        return new ResponseEntity<>(productOrdersService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增生产入库单据")
    @ApiOperation("新增查询生产入库单据")
    @PreAuthorize("@el.check('productOrders:add')")
    public ResponseEntity create(@Validated @RequestBody OrderVo vo){
        return new ResponseEntity<>(productOrdersService.insert(vo),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改生产入库单据")
    @ApiOperation("修改查询生产入库单据")
    @PreAuthorize("@el.check('productOrders:edit')")
    public ResponseEntity update(@Validated @RequestBody OrderVo vo){
        productOrdersService.updateById(vo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除生产入库单据")
    @ApiOperation("删除查询生产入库单据")
    @PreAuthorize("@el.check('productOrders:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        productOrdersService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出生产入库单据")
    @ApiOperation("导出查询生产入库单据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('productOrders:list')")
    public void download(HttpServletResponse response, TbOrdersQueryParam query) throws IOException {
        productOrdersService.download(productOrdersService.queryAll(query), response);
    }*/

    @GetMapping("/getManagerSelect")
    @Log("获取经办人下拉菜单")
    @ApiOperation("获取经办人下拉菜单")
    @PreAuthorize("@el.check('productOrders:add','productOrders:edit')")
    public ResponseEntity getManagerSelect(){
        return new ResponseEntity<>(productOrdersService.getManagerSelect(),HttpStatus.OK);
    }

    @GetMapping("/getOrderNo")
    @Log("获取新增单号")
    @ApiOperation("获取新增单号")
    @PreAuthorize("@el.check('productOrders:add','productOrders:edit')")
    public ResponseEntity getOrderNo(){
        return new ResponseEntity<>(productOrdersService.getOrderNo(),HttpStatus.OK);
    }

    @GetMapping("/getGoodChooseList")
    @Log("获取选择货品列表")
    @ApiOperation("获取选择货品列表")
    @PreAuthorize("@el.check('productOrders:add','productOrders:edit')")
    public ResponseEntity getGoodChooseList(TbGoodsInfoQueryParam query, Pageable pageable){
        return new ResponseEntity<>(productOrdersService.getGoodChooseList(query, pageable),HttpStatus.OK);
    }

    @GetMapping("/getOrderGoodList")
    @Log("获取单据货品列表")
    @ApiOperation("获取单据货品列表")
    @PreAuthorize("@el.check('productOrders:edit')")
    public ResponseEntity getOrderGoodList(Long id, Long whId){
        return new ResponseEntity<>(productOrdersService.getOrderGoodList(id, whId),HttpStatus.OK);
    }

    @PutMapping("/approveOrders")
    @Log("审批单据")
    @ApiOperation("审批单据")
    @PreAuthorize("@el.check('productOrders:approve')")
    public ResponseEntity approveOrders(@RequestBody Set<Long> ids) {
        productOrdersService.approveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reApproveOrders")
    @Log("反审单据")
    @ApiOperation("反审单据")
    @PreAuthorize("@el.check('productOrders:reApprove')")
    public ResponseEntity reApproveOrders(@RequestBody Set<Long> ids) {
        productOrdersService.reApproveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/printOrderReport")
    @Log("打印单据")
    @ApiOperation("打印单据")
    @PreAuthorize("@el.check('productOrders:print')")
    public ResponseEntity printOrderReport(Long id, HttpServletResponse response) throws Exception {
        productOrdersService.printOrderReport(id, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrderPrintingInfo")
    @Log("获取打印单据信息")
    @ApiOperation("获取打印单据信息")
    @PreAuthorize("@el.check('productOrders:print')")
    public ResponseEntity getOrderPrintingInfo(Long id) throws Exception {
        return new ResponseEntity<>(productOrdersService.getOrderPrintingInfo(id), HttpStatus.OK);
    }
}
