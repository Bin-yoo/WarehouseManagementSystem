package me.zhengjie.modules.waitapproveorders.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.searchorders.service.dto.SearchOrdersQueryParam;
import me.zhengjie.modules.waitapproveorders.service.WaitApproveOrdersService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
* @author LiangBin
* @date 2022-01-03
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "待审单据管理")
@RequestMapping("/api/waitApproveOrders")
public class WaitApproveOrdersController {

    private final WaitApproveOrdersService waitApproveOrdersService;

    @GetMapping
    @Log("查询待审单据")
    @ApiOperation("查询待审单据")
    @PreAuthorize("@el.check('waitApproveOrders:list')")
    public ResponseEntity query(SearchOrdersQueryParam query, Pageable pageable){
        return new ResponseEntity<>(waitApproveOrdersService.queryAll(query,pageable),HttpStatus.OK);
    }

    /*
    @Log("导出待审单据")
    @ApiOperation("导出待审单据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('waitApproveOrdersService:list')")
    public void download(HttpServletResponse response, TbOrdersQueryParam query) throws IOException {
        waitApproveOrdersServiceService.download(waitApproveOrdersServiceService.queryAll(query), response);
    }*/

    @GetMapping("/getOrderGoodList")
    @Log("获取单据货品列表")
    @ApiOperation("获取单据货品列表")
    @PreAuthorize("@el.check('waitApproveOrders:detail')")
    public ResponseEntity getOrderGoodList(Long id){
        return new ResponseEntity<>(waitApproveOrdersService.getOrderGoodList(id),HttpStatus.OK);
    }

    @PutMapping("/approveOrders")
    @Log("审批单据")
    @ApiOperation("审批单据")
    @PreAuthorize("@el.check('waitApproveOrders:approve')")
    public ResponseEntity approveOrders(@RequestBody Set<Long> ids) {
        waitApproveOrdersService.approveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/reApproveOrders")
    @Log("反审单据")
    @ApiOperation("反审单据")
    @PreAuthorize("@el.check('waitApproveOrders:reApprove')")
    public ResponseEntity reApproveOrders(@RequestBody Set<Long> ids) {
        waitApproveOrdersService.reApproveOrders(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/printOrderReport")
    @Log("打印单据")
    @ApiOperation("打印单据")
    @PreAuthorize("@el.check('waitApproveOrders:print')")
    public ResponseEntity printOrderReport(Long id, HttpServletResponse response) throws Exception {
        waitApproveOrdersService.printOrderReport(id, response);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getOrderPrintingInfo")
    @Log("获取打印单据信息")
    @ApiOperation("获取打印单据信息")
    @PreAuthorize("@el.check('waitApproveOrders:print')")
    public ResponseEntity getOrderPrintingInfo(Long id) throws Exception {
        return new ResponseEntity<>(waitApproveOrdersService.getOrderPrintingInfo(id), HttpStatus.OK);
    }
}
