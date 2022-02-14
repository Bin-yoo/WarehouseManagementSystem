package me.zhengjie.modules.orders.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.orders.domain.vo.OrderVo;
import me.zhengjie.modules.orders.service.PurchaseOrdersService;
import me.zhengjie.modules.orders.service.dto.TbOrdersQueryParam;
import net.sf.jasperreports.engine.JRException;
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
@Api(tags = "采购入库单据管理")
@RequestMapping("/api/purchaseOrders")
public class PurchaseOrdersController {

    private final PurchaseOrdersService purchaseOrdersService;

    @GetMapping
    @Log("查询采购入库单据")
    @ApiOperation("查询采购入库单据")
    @PreAuthorize("@el.check('purchaseOrders:list')")
    public ResponseEntity query(TbOrdersQueryParam query, Pageable pageable){
        return new ResponseEntity<>(purchaseOrdersService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增采购入库单据")
    @ApiOperation("新增采购入库单据")
    @PreAuthorize("@el.check('purchaseOrders:add')")
    public ResponseEntity create(@Validated @RequestBody OrderVo vo){
        return new ResponseEntity<>(purchaseOrdersService.insert(vo),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改采购入库单据")
    @ApiOperation("修改采购入库单据")
    @PreAuthorize("@el.check('purchaseOrders:edit')")
    public ResponseEntity update(@Validated @RequestBody OrderVo vo){
        purchaseOrdersService.updateById(vo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除采购入库单据")
    @ApiOperation("删除采购入库单据")
    @PreAuthorize("@el.check('purchaseOrders:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        purchaseOrdersService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出采购入库单据")
    @ApiOperation("导出采购入库单据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('purchaseOrders:list')")
    public void download(HttpServletResponse response, TbOrdersQueryParam query) throws IOException {
        purchaseOrdersService.download(purchaseOrdersService.queryAll(query), response);
    }*/

    @GetMapping("/getSupplierSelect")
    @Log("获取供应商下拉菜单")
    @ApiOperation("获取供应商下拉菜单")
    @PreAuthorize("@el.check('purchaseOrders:list')")
    public ResponseEntity getSupplierSelect(){
        return new ResponseEntity<>(purchaseOrdersService.getSupplierSelect(),HttpStatus.OK);
    }

    @GetMapping("/getManagerSelect")
    @Log("获取经办人下拉菜单")
    @ApiOperation("获取经办人下拉菜单")
    @PreAuthorize("@el.check('purchaseOrders:add','purchaseOrders:edit')")
    public ResponseEntity getManagerSelect(){
        return new ResponseEntity<>(purchaseOrdersService.getManagerSelect(),HttpStatus.OK);
    }

    @GetMapping("/getOrderNo")
    @Log("获取新增单号")
    @ApiOperation("获取新增单号")
    @PreAuthorize("@el.check('purchaseOrders:add','purchaseOrders:edit')")
    public ResponseEntity getOrderNo(){
        return new ResponseEntity<>(purchaseOrdersService.getOrderNo(),HttpStatus.OK);
    }

    @GetMapping("/getGoodChooseList")
    @Log("获取选择货品列表")
    @ApiOperation("获取选择货品列表")
    @PreAuthorize("@el.check('purchaseOrders:add','purchaseOrders:edit')")
    public ResponseEntity getGoodChooseList(TbGoodsInfoQueryParam query, Pageable pageable){
        return new ResponseEntity<>(purchaseOrdersService.getGoodChooseList(query, pageable),HttpStatus.OK);
    }

    @GetMapping("/getOrderGoodList")
    @Log("获取单据货品列表")
    @ApiOperation("获取单据货品列表")
    @PreAuthorize("@el.check('purchaseOrders:edit')")
    public ResponseEntity getOrderGoodList(Long id){
        return new ResponseEntity<>(purchaseOrdersService.getOrderGoodList(id),HttpStatus.OK);
    }

    @PutMapping("/approveOrders")
    @Log("审批单据")
    @ApiOperation("审批单据")
    @PreAuthorize("@el.check('purchaseOrders:approve')")
    public ResponseEntity approveOrders(@RequestBody Set<Long> ids) {
        purchaseOrdersService.approveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reApproveOrders")
    @Log("反审单据")
    @ApiOperation("反审单据")
    @PreAuthorize("@el.check('purchaseOrders:approve')")
    public ResponseEntity reApproveOrders(@RequestBody Set<Long> ids) {
        purchaseOrdersService.reApproveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/printOrderReport")
    @Log("打印单据")
    @ApiOperation("打印单据")
    @PreAuthorize("@el.check('purchaseOrders:list')")
    public ResponseEntity printOrderReport(Long id, HttpServletResponse response) throws Exception {
        purchaseOrdersService.printOrderReport(id, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrderPrintingInfo")
    @Log("获取打印单据信息")
    @ApiOperation("获取打印单据信息")
    @PreAuthorize("@el.check('purchaseOrders:list')")
    public ResponseEntity getOrderPrintingInfo(Long id) throws Exception {
        return new ResponseEntity<>(purchaseOrdersService.getOrderPrintingInfo(id), HttpStatus.OK);
    }
}
