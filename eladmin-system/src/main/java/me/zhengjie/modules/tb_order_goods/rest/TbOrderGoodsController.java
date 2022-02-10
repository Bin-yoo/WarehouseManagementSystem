package me.zhengjie.modules.tb_order_goods.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tb_order_goods.domain.TbOrderGoods;
import me.zhengjie.modules.tb_order_goods.service.TbOrderGoodsService;
import me.zhengjie.modules.tb_order_goods.service.dto.TbOrderGoodsDto;
import me.zhengjie.modules.tb_order_goods.service.dto.TbOrderGoodsQueryParam;
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
* @date 2022-02-10
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "tb_order_goods管理")
@RequestMapping("/api/tbOrderGoods")
public class TbOrderGoodsController {

    private final TbOrderGoodsService tbOrderGoodsService;

    @GetMapping
    @Log("查询tb_order_goods")
    @ApiOperation("查询tb_order_goods")
    @PreAuthorize("@el.check('tbOrderGoods:list')")
    public ResponseEntity query(TbOrderGoodsQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbOrderGoodsService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_order_goods")
    @ApiOperation("新增tb_order_goods")
    @PreAuthorize("@el.check('tbOrderGoods:add')")
    public ResponseEntity create(@Validated @RequestBody TbOrderGoodsDto resources){
        return new ResponseEntity<>(tbOrderGoodsService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_order_goods")
    @ApiOperation("修改tb_order_goods")
    @PreAuthorize("@el.check('tbOrderGoods:edit')")
    public ResponseEntity update(@Validated @RequestBody TbOrderGoodsDto resources){
        tbOrderGoodsService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_order_goods")
    @ApiOperation("删除tb_order_goods")
    @PreAuthorize("@el.check('tbOrderGoods:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbOrderGoodsService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出tb_order_goods")
    @ApiOperation("导出tb_order_goods")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbOrderGoods:list')")
    public void download(HttpServletResponse response, TbOrderGoodsQueryParam query) throws IOException {
        tbOrderGoodsService.download(tbOrderGoodsService.queryAll(query), response);
    }*/

}
