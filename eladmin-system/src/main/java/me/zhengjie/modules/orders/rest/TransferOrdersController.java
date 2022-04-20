package me.zhengjie.modules.orders.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.orders.domain.vo.OrderVo;
import me.zhengjie.modules.orders.service.TransferOrdersService;
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
@Api(tags = "仓库调拨单据管理")
@RequestMapping("/api/transferOrders")
public class TransferOrdersController {

    private final TransferOrdersService transferOrdersService;

    @GetMapping
    @Log("查询仓库调拨单据")
    @ApiOperation("查询仓库调拨单据")
    @PreAuthorize("@el.check('transferOrders:list')")
    public ResponseEntity query(TbOrdersQueryParam query, Pageable pageable){
        return new ResponseEntity<>(transferOrdersService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增仓库调拨单据")
    @ApiOperation("新增查询仓库调拨单据")
    @PreAuthorize("@el.check('transferOrders:add')")
    public ResponseEntity create(@Validated @RequestBody OrderVo vo){
        return new ResponseEntity<>(transferOrdersService.insert(vo),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改仓库调拨单据")
    @ApiOperation("修改查询仓库调拨单据")
    @PreAuthorize("@el.check('transferOrders:edit')")
    public ResponseEntity update(@Validated @RequestBody OrderVo vo){
        transferOrdersService.updateById(vo);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除仓库调拨单据")
    @ApiOperation("删除查询仓库调拨单据")
    @PreAuthorize("@el.check('transferOrders:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        transferOrdersService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出仓库调拨单据")
    @ApiOperation("导出查询仓库调拨单据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('transferOrders:list')")
    public void download(HttpServletResponse response, TbOrdersQueryParam query) throws IOException {
        transferOrdersService.download(transferOrdersService.queryAll(query), response);
    }*/

    @GetMapping("/getManagerSelect")
    @Log("获取经办人下拉菜单")
    @ApiOperation("获取经办人下拉菜单")
    @PreAuthorize("@el.check('transferOrders:add','transferOrders:edit')")
    public ResponseEntity getManagerSelect(){
        return new ResponseEntity<>(transferOrdersService.getManagerSelect(),HttpStatus.OK);
    }

    @GetMapping("/getOrderNo")
    @Log("获取新增单号")
    @ApiOperation("获取新增单号")
    @PreAuthorize("@el.check('transferOrders:add','transferOrders:edit')")
    public ResponseEntity getOrderNo(){
        return new ResponseEntity<>(transferOrdersService.getOrderNo(),HttpStatus.OK);
    }

    @GetMapping("/getGoodChooseList")
    @Log("获取选择货品列表")
    @ApiOperation("获取选择货品列表")
    @PreAuthorize("@el.check('transferOrders:add','transferOrders:edit')")
    public ResponseEntity getGoodChooseList(TbGoodsInfoQueryParam query, Pageable pageable){
        return new ResponseEntity<>(transferOrdersService.getGoodChooseList(query, pageable),HttpStatus.OK);
    }

    @GetMapping("/getOrderGoodList")
    @Log("获取单据货品列表")
    @ApiOperation("获取单据货品列表")
    @PreAuthorize("@el.check('transferOrders:edit')")
    public ResponseEntity getOrderGoodList(Long id, Long whId){
        return new ResponseEntity<>(transferOrdersService.getOrderGoodList(id, whId),HttpStatus.OK);
    }

    @PutMapping("/approveOrders")
    @Log("审批单据")
    @ApiOperation("审批单据")
    @PreAuthorize("@el.check('transferOrders:approve')")
    public ResponseEntity approveOrders(@RequestBody Set<Long> ids) {
        transferOrdersService.approveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reApproveOrders")
    @Log("反审单据")
    @ApiOperation("反审单据")
    @PreAuthorize("@el.check('transferOrders:reApprove')")
    public ResponseEntity reApproveOrders(@RequestBody Set<Long> ids) {
        transferOrdersService.reApproveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/printOrderReport")
    @Log("打印单据")
    @ApiOperation("打印单据")
    @PreAuthorize("@el.check('transferOrders:print')")
    public ResponseEntity printOrderReport(Long id, HttpServletResponse response) throws Exception {
        transferOrdersService.printOrderReport(id, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrderPrintingInfo")
    @Log("获取打印单据信息")
    @ApiOperation("获取打印单据信息")
    @PreAuthorize("@el.check('transferOrders:print')")
    public ResponseEntity getOrderPrintingInfo(Long id) throws Exception {
        return new ResponseEntity<>(transferOrdersService.getOrderPrintingInfo(id), HttpStatus.OK);
    }
}
