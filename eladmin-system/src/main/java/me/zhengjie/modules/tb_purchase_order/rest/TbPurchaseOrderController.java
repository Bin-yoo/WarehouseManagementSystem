package me.zhengjie.modules.tb_purchase_order.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tb_purchase_order.domain.TbPurchaseOrder;
import me.zhengjie.modules.tb_purchase_order.service.TbPurchaseOrderService;
import me.zhengjie.modules.tb_purchase_order.service.dto.TbPurchaseOrderDto;
import me.zhengjie.modules.tb_purchase_order.service.dto.TbPurchaseOrderQueryParam;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "tb_purchase_order管理")
@RequestMapping("/api/tbPurchaseOrder")
public class TbPurchaseOrderController {

    private final TbPurchaseOrderService tbPurchaseOrderService;

    @GetMapping
    @Log("查询tb_purchase_order")
    @ApiOperation("查询tb_purchase_order")
    @PreAuthorize("@el.check('tbPurchaseOrder:list')")
    public ResponseEntity query(TbPurchaseOrderQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbPurchaseOrderService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_purchase_order")
    @ApiOperation("新增tb_purchase_order")
    @PreAuthorize("@el.check('tbPurchaseOrder:add')")
    public ResponseEntity create(@Validated @RequestBody TbPurchaseOrderDto resources){
        return new ResponseEntity<>(tbPurchaseOrderService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_purchase_order")
    @ApiOperation("修改tb_purchase_order")
    @PreAuthorize("@el.check('tbPurchaseOrder:edit')")
    public ResponseEntity update(@Validated @RequestBody TbPurchaseOrderDto resources){
        tbPurchaseOrderService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_purchase_order")
    @ApiOperation("删除tb_purchase_order")
    @PreAuthorize("@el.check('tbPurchaseOrder:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbPurchaseOrderService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出tb_purchase_order")
    @ApiOperation("导出tb_purchase_order")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbPurchaseOrder:list')")
    public void download(HttpServletResponse response, TbPurchaseOrderQueryParam query) throws IOException {
        tbPurchaseOrderService.download(tbPurchaseOrderService.queryAll(query), response);
    }*/

}
