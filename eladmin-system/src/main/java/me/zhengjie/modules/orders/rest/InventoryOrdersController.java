package me.zhengjie.modules.orders.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.orders.domain.vo.OrderVo;
import me.zhengjie.modules.orders.service.InventoryOrdersService;
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
@Api(tags = "库存盘点单据管理")
@RequestMapping("/api/inventoryOrders")
public class InventoryOrdersController {

    private final InventoryOrdersService inventoryOrdersService;

    @GetMapping
    @Log("查询库存盘点单据")
    @ApiOperation("查询库存盘点单据")
    @PreAuthorize("@el.check('inventoryOrders:list')")
    public ResponseEntity query(TbOrdersQueryParam query, Pageable pageable){
        return new ResponseEntity<>(inventoryOrdersService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增库存盘点单据")
    @ApiOperation("新增库存盘点单据")
    @PreAuthorize("@el.check('inventoryOrders:add')")
    public ResponseEntity create(@Validated @RequestBody OrderVo vo){
        return new ResponseEntity<>(inventoryOrdersService.insert(vo),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改库存盘点单据")
    @ApiOperation("修改库存盘点单据")
    @PreAuthorize("@el.check('inventoryOrders:edit')")
    public ResponseEntity update(@Validated @RequestBody OrderVo vo){
        inventoryOrdersService.updateById(vo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除库存盘点单据")
    @ApiOperation("删除库存盘点单据")
    @PreAuthorize("@el.check('inventoryOrders:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        inventoryOrdersService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出库存盘点单据")
    @ApiOperation("导出库存盘点单据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('inventoryOrders:list')")
    public void download(HttpServletResponse response, TbOrdersQueryParam query) throws IOException {
        inventoryOrdersService.download(inventoryOrdersService.queryAll(query), response);
    }*/

    @GetMapping("/getManagerSelect")
    @Log("获取经办人下拉菜单")
    @ApiOperation("获取经办人下拉菜单")
    @PreAuthorize("@el.check('inventoryOrders:list')")
    public ResponseEntity getManagerSelect(){
        return new ResponseEntity<>(inventoryOrdersService.getManagerSelect(),HttpStatus.OK);
    }

    @GetMapping("/getOrderNo")
    @Log("获取新增单号")
    @ApiOperation("获取新增单号")
    @PreAuthorize("@el.check('inventoryOrders:list')")
    public ResponseEntity getOrderNo(){
        return new ResponseEntity<>(inventoryOrdersService.getOrderNo(),HttpStatus.OK);
    }

    @GetMapping("/getGoodChooseList")
    @Log("获取选择货品列表")
    @ApiOperation("获取选择货品列表")
    @PreAuthorize("@el.check('inventoryOrders:list')")
    public ResponseEntity getGoodChooseList(TbGoodsInfoQueryParam query, Pageable pageable){
        return new ResponseEntity<>(inventoryOrdersService.getGoodChooseList(query, pageable),HttpStatus.OK);
    }

    @GetMapping("/getOrderGoodList")
    @Log("获取单据货品列表")
    @ApiOperation("获取单据货品列表")
    @PreAuthorize("@el.check('inventoryOrders:list')")
    public ResponseEntity getOrderGoodList(Long id){
        return new ResponseEntity<>(inventoryOrdersService.getOrderGoodList(id),HttpStatus.OK);
    }

    @PutMapping("/approveOrders")
    @Log("审批单据")
    @ApiOperation("审批单据")
    @PreAuthorize("@el.check('inventoryOrders:approve')")
    public ResponseEntity approveOrders(@RequestBody Set<Long> ids) {
        inventoryOrdersService.approveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reApproveOrders")
    @Log("反审单据")
    @ApiOperation("反审单据")
    @PreAuthorize("@el.check('inventoryOrders:reApprove')")
    public ResponseEntity reApproveOrders(@RequestBody Set<Long> ids) {
        inventoryOrdersService.reApproveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/printOrderReport")
    @Log("打印单据")
    @ApiOperation("打印单据")
    @PreAuthorize("@el.check('inventoryOrders:print')")
    public ResponseEntity printOrderReport(Long id, HttpServletResponse response) throws Exception {
        inventoryOrdersService.printOrderReport(id, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrderPrintingInfo")
    @Log("获取打印单据信息")
    @ApiOperation("获取打印单据信息")
    @PreAuthorize("@el.check('inventoryOrders:print')")
    public ResponseEntity getOrderPrintingInfo(Long id) throws Exception {
        return new ResponseEntity<>(inventoryOrdersService.getOrderPrintingInfo(id), HttpStatus.OK);
    }
}
