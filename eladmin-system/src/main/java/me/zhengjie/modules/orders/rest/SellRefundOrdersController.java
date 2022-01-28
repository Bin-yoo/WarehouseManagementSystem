package me.zhengjie.modules.orders.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.orders.domain.vo.OrderVo;
import me.zhengjie.modules.orders.service.SellRefundOrdersService;
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
@Api(tags = "销售退货单据管理")
@RequestMapping("/api/sellRefundOrders")
public class SellRefundOrdersController {

    private final SellRefundOrdersService sellRefundOrdersService;

    @GetMapping
    @Log("查询销售退货单据")
    @ApiOperation("查询销售退货单据")
    @PreAuthorize("@el.check('sellRefundOrders:list')")
    public ResponseEntity query(TbOrdersQueryParam query, Pageable pageable){
        return new ResponseEntity<>(sellRefundOrdersService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增销售退货单据")
    @ApiOperation("新增销售退货单据")
    @PreAuthorize("@el.check('sellRefundOrders:add')")
    public ResponseEntity create(@Validated @RequestBody OrderVo vo){
        return new ResponseEntity<>(sellRefundOrdersService.insert(vo),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改销售退货单据")
    @ApiOperation("修改销售退货单据")
    @PreAuthorize("@el.check('sellRefundOrders:edit')")
    public ResponseEntity update(@Validated @RequestBody OrderVo vo){
        sellRefundOrdersService.updateById(vo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除销售退货单据")
    @ApiOperation("删除销售退货单据")
    @PreAuthorize("@el.check('sellRefundOrders:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        sellRefundOrdersService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出销售退货单据")
    @ApiOperation("导出销售退货单据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sellRefundOrders:list')")
    public void download(HttpServletResponse response, TbOrdersQueryParam query) throws IOException {
        sellRefundOrdersService.download(sellRefundOrdersService.queryAll(query), response);
    }*/

    @GetMapping("/getCustomerSelect")
    @Log("获取客户下拉菜单")
    @ApiOperation("获取客户下拉菜单")
    @PreAuthorize("@el.check('sellRefundOrders:list')")
    public ResponseEntity getCustomerSelect(){
        return new ResponseEntity<>(sellRefundOrdersService.getCustomerSelect(),HttpStatus.OK);
    }

    @GetMapping("/getManagerSelect")
    @Log("获取经办人下拉菜单")
    @ApiOperation("获取经办人下拉菜单")
    @PreAuthorize("@el.check('sellRefundOrders:add','sellRefundOrders:edit')")
    public ResponseEntity getManagerSelect(){
        return new ResponseEntity<>(sellRefundOrdersService.getManagerSelect(),HttpStatus.OK);
    }

    @GetMapping("/getOrderNo")
    @Log("获取新增单号")
    @ApiOperation("获取新增单号")
    @PreAuthorize("@el.check('sellRefundOrders:add','sellRefundOrders:edit')")
    public ResponseEntity getOrderNo(){
        return new ResponseEntity<>(sellRefundOrdersService.getOrderNo(),HttpStatus.OK);
    }

    @GetMapping("/getGoodChooseList")
    @Log("获取选择货品列表")
    @ApiOperation("获取选择货品列表")
    @PreAuthorize("@el.check('sellRefundOrders:add','sellRefundOrders:edit')")
    public ResponseEntity getGoodChooseList(TbGoodsInfoQueryParam query, Pageable pageable){
        return new ResponseEntity<>(sellRefundOrdersService.getGoodChooseList(query, pageable),HttpStatus.OK);
    }

    @GetMapping("/getOrderGoodList")
    @Log("获取单据货品列表")
    @ApiOperation("获取单据货品列表")
    @PreAuthorize("@el.check('sellRefundOrders:edit')")
    public ResponseEntity getOrderGoodList(String id){
        return new ResponseEntity<>(sellRefundOrdersService.getOrderGoodList(id),HttpStatus.OK);
    }

    @PutMapping("/approveOrders")
    @Log("审批单据")
    @ApiOperation("审批单据")
    @PreAuthorize("@el.check('sellRefundOrders:approve')")
    public ResponseEntity approveOrders(@RequestBody Set<Long> ids) {
        sellRefundOrdersService.approveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reApproveOrders")
    @Log("反审单据")
    @ApiOperation("反审单据")
    @PreAuthorize("@el.check('sellRefundOrders:approve')")
    public ResponseEntity reApproveOrders(@RequestBody Set<Long> ids) {
        sellRefundOrdersService.reApproveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/printOrderReport")
    @Log("打印单据")
    @ApiOperation("打印单据")
    @PreAuthorize("@el.check('sellRefundOrders:list')")
    public ResponseEntity printOrderReport(String id, HttpServletResponse response) throws Exception {
        sellRefundOrdersService.printOrderReport(id, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrderPrintingInfo")
    @Log("获取打印单据信息")
    @ApiOperation("获取打印单据信息")
    @PreAuthorize("@el.check('sellRefundOrders:list')")
    public ResponseEntity getOrderPrintingInfo(String id) throws Exception {
        return new ResponseEntity<>(sellRefundOrdersService.getOrderPrintingInfo(id), HttpStatus.OK);
    }

    @GetMapping("/getOrderChooseList")
    @Log("获取选择订单列表")
    @ApiOperation("获取选择订单列表")
    @PreAuthorize("@el.check('sellRefundOrders:add','sellRefundOrders:edit')")
    public ResponseEntity getOrderChooseList(TbOrdersQueryParam query, Pageable pageable) throws Exception {
        return new ResponseEntity<>(sellRefundOrdersService.getOrderChooseList(query, pageable), HttpStatus.OK);
    }
}
