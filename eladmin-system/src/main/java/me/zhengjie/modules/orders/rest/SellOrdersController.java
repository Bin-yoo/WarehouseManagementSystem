package me.zhengjie.modules.orders.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.orders.domain.vo.OrderVo;
import me.zhengjie.modules.orders.service.SellOrdersService;
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
@Api(tags = "销售提货单据管理")
@RequestMapping("/api/sellOrders")
public class SellOrdersController {

    private final SellOrdersService sellOrdersService;

    @GetMapping
    @Log("查询销售提货单据")
    @ApiOperation("查询销售提货单据")
    @PreAuthorize("@el.check('sellOrders:list')")
    public ResponseEntity query(TbOrdersQueryParam query, Pageable pageable){
        return new ResponseEntity<>(sellOrdersService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增销售提货单据")
    @ApiOperation("新增销售提货单据")
    @PreAuthorize("@el.check('sellOrders:add')")
    public ResponseEntity create(@Validated @RequestBody OrderVo vo){
        return new ResponseEntity<>(sellOrdersService.insert(vo),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改销售提货单据")
    @ApiOperation("修改销售提货单据")
    @PreAuthorize("@el.check('sellOrders:edit')")
    public ResponseEntity update(@Validated @RequestBody OrderVo vo){
        sellOrdersService.updateById(vo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除销售提货单据")
    @ApiOperation("删除销售提货单据")
    @PreAuthorize("@el.check('sellOrders:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        sellOrdersService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出销售提货单据")
    @ApiOperation("导出销售提货单据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sellOrders:list')")
    public void download(HttpServletResponse response, TbOrdersQueryParam query) throws IOException {
        sellOrdersService.download(sellOrdersService.queryAll(query), response);
    }*/

    @GetMapping("/getCustomerSelect")
    @Log("获取客户下拉菜单")
    @ApiOperation("获取客户下拉菜单")
    @PreAuthorize("@el.check('sellOrders:list')")
    public ResponseEntity getCustomerSelect(){
        return new ResponseEntity<>(sellOrdersService.getCustomerSelect(),HttpStatus.OK);
    }

    @GetMapping("/getManagerSelect")
    @Log("获取经办人下拉菜单")
    @ApiOperation("获取经办人下拉菜单")
    @PreAuthorize("@el.check('sellOrders:add','sellOrders:edit')")
    public ResponseEntity getManagerSelect(){
        return new ResponseEntity<>(sellOrdersService.getManagerSelect(),HttpStatus.OK);
    }

    @GetMapping("/getOrderNo")
    @Log("获取新增单号")
    @ApiOperation("获取新增单号")
    @PreAuthorize("@el.check('sellOrders:add','sellOrders:edit')")
    public ResponseEntity getOrderNo(){
        return new ResponseEntity<>(sellOrdersService.getOrderNo(),HttpStatus.OK);
    }

    @GetMapping("/getGoodChooseList")
    @Log("获取选择货品列表")
    @ApiOperation("获取选择货品列表")
    @PreAuthorize("@el.check('sellOrders:add','sellOrders:edit')")
    public ResponseEntity getGoodChooseList(TbGoodsInfoQueryParam query, Pageable pageable){
        return new ResponseEntity<>(sellOrdersService.getGoodChooseList(query, pageable),HttpStatus.OK);
    }

    @GetMapping("/getOrderGoodList")
    @Log("获取单据货品列表")
    @ApiOperation("获取单据货品列表")
    @PreAuthorize("@el.check('sellOrders:edit')")
    public ResponseEntity getOrderGoodList(Long id, Long whId){
        return new ResponseEntity<>(sellOrdersService.getOrderGoodList(id, whId),HttpStatus.OK);
    }

    @PutMapping("/approveOrders")
    @Log("审批单据")
    @ApiOperation("审批单据")
    @PreAuthorize("@el.check('sellOrders:approve')")
    public ResponseEntity approveOrders(@RequestBody Set<Long> ids) {
        sellOrdersService.approveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reApproveOrders")
    @Log("反审单据")
    @ApiOperation("反审单据")
    @PreAuthorize("@el.check('sellOrders:approve')")
    public ResponseEntity reApproveOrders(@RequestBody Set<Long> ids) {
        sellOrdersService.reApproveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/printOrderReport")
    @Log("打印单据")
    @ApiOperation("打印单据")
    @PreAuthorize("@el.check('sellOrders:list')")
    public ResponseEntity printOrderReport(Long id, HttpServletResponse response) throws Exception {
        sellOrdersService.printOrderReport(id, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrderPrintingInfo")
    @Log("获取打印单据信息")
    @ApiOperation("获取打印单据信息")
    @PreAuthorize("@el.check('sellOrders:list')")
    public ResponseEntity getOrderPrintingInfo(Long id) throws Exception {
        return new ResponseEntity<>(sellOrdersService.getOrderPrintingInfo(id), HttpStatus.OK);
    }
}
