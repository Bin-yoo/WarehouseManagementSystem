package me.zhengjie.modules.tb_purchase_order_goods.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tb_purchase_order_goods.domain.TbPurchaseOrderGoods;
import me.zhengjie.modules.tb_purchase_order_goods.service.TbPurchaseOrderGoodsService;
import me.zhengjie.modules.tb_purchase_order_goods.service.dto.TbPurchaseOrderGoodsDto;
import me.zhengjie.modules.tb_purchase_order_goods.service.dto.TbPurchaseOrderGoodsQueryParam;
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
@Api(tags = "tb_purchase_order_goods管理")
@RequestMapping("/api/tbPurchaseOrderGoods")
public class TbPurchaseOrderGoodsController {

    private final TbPurchaseOrderGoodsService tbPurchaseOrderGoodsService;

    @GetMapping
    @Log("查询tb_purchase_order_goods")
    @ApiOperation("查询tb_purchase_order_goods")
    @PreAuthorize("@el.check('tbPurchaseOrderGoods:list')")
    public ResponseEntity query(TbPurchaseOrderGoodsQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbPurchaseOrderGoodsService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_purchase_order_goods")
    @ApiOperation("新增tb_purchase_order_goods")
    @PreAuthorize("@el.check('tbPurchaseOrderGoods:add')")
    public ResponseEntity create(@Validated @RequestBody TbPurchaseOrderGoodsDto resources){
        return new ResponseEntity<>(tbPurchaseOrderGoodsService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_purchase_order_goods")
    @ApiOperation("修改tb_purchase_order_goods")
    @PreAuthorize("@el.check('tbPurchaseOrderGoods:edit')")
    public ResponseEntity update(@Validated @RequestBody TbPurchaseOrderGoodsDto resources){
        tbPurchaseOrderGoodsService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_purchase_order_goods")
    @ApiOperation("删除tb_purchase_order_goods")
    @PreAuthorize("@el.check('tbPurchaseOrderGoods:del')")
    public ResponseEntity delete(@RequestBody Set<String> ids) {
        tbPurchaseOrderGoodsService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出tb_purchase_order_goods")
    @ApiOperation("导出tb_purchase_order_goods")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbPurchaseOrderGoods:list')")
    public void download(HttpServletResponse response, TbPurchaseOrderGoodsQueryParam query) throws IOException {
        tbPurchaseOrderGoodsService.download(tbPurchaseOrderGoodsService.queryAll(query), response);
    }*/

}
