package me.zhengjie.modules.tb_product_order_goods.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tb_product_order_goods.domain.TbProductOrderGoods;
import me.zhengjie.modules.tb_product_order_goods.service.TbProductOrderGoodsService;
import me.zhengjie.modules.tb_product_order_goods.service.dto.TbProductOrderGoodsDto;
import me.zhengjie.modules.tb_product_order_goods.service.dto.TbProductOrderGoodsQueryParam;
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
@Api(tags = "tb_product_order_goods管理")
@RequestMapping("/api/tbProductOrderGoods")
public class TbProductOrderGoodsController {

    private final TbProductOrderGoodsService tbProductOrderGoodsService;

    @GetMapping
    @Log("查询tb_product_order_goods")
    @ApiOperation("查询tb_product_order_goods")
    @PreAuthorize("@el.check('tbProductOrderGoods:list')")
    public ResponseEntity query(TbProductOrderGoodsQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbProductOrderGoodsService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_product_order_goods")
    @ApiOperation("新增tb_product_order_goods")
    @PreAuthorize("@el.check('tbProductOrderGoods:add')")
    public ResponseEntity create(@Validated @RequestBody TbProductOrderGoodsDto resources){
        return new ResponseEntity<>(tbProductOrderGoodsService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_product_order_goods")
    @ApiOperation("修改tb_product_order_goods")
    @PreAuthorize("@el.check('tbProductOrderGoods:edit')")
    public ResponseEntity update(@Validated @RequestBody TbProductOrderGoodsDto resources){
        tbProductOrderGoodsService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_product_order_goods")
    @ApiOperation("删除tb_product_order_goods")
    @PreAuthorize("@el.check('tbProductOrderGoods:del')")
    public ResponseEntity delete(@RequestBody Set<String> ids) {
        tbProductOrderGoodsService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出tb_product_order_goods")
    @ApiOperation("导出tb_product_order_goods")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbProductOrderGoods:list')")
    public void download(HttpServletResponse response, TbProductOrderGoodsQueryParam query) throws IOException {
        tbProductOrderGoodsService.download(tbProductOrderGoodsService.queryAll(query), response);
    }*/

}
