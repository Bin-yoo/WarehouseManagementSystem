package me.zhengjie.modules.tb_return_order_goods.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tb_return_order_goods.domain.TbReturnOrderGoods;
import me.zhengjie.modules.tb_return_order_goods.service.TbReturnOrderGoodsService;
import me.zhengjie.modules.tb_return_order_goods.service.dto.TbReturnOrderGoodsDto;
import me.zhengjie.modules.tb_return_order_goods.service.dto.TbReturnOrderGoodsQueryParam;
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
@Api(tags = "tb_return_order_goods管理")
@RequestMapping("/api/tbReturnOrderGoods")
public class TbReturnOrderGoodsController {

    private final TbReturnOrderGoodsService tbReturnOrderGoodsService;

    @GetMapping
    @Log("查询tb_return_order_goods")
    @ApiOperation("查询tb_return_order_goods")
    @PreAuthorize("@el.check('tbReturnOrderGoods:list')")
    public ResponseEntity query(TbReturnOrderGoodsQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbReturnOrderGoodsService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_return_order_goods")
    @ApiOperation("新增tb_return_order_goods")
    @PreAuthorize("@el.check('tbReturnOrderGoods:add')")
    public ResponseEntity create(@Validated @RequestBody TbReturnOrderGoodsDto resources){
        return new ResponseEntity<>(tbReturnOrderGoodsService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_return_order_goods")
    @ApiOperation("修改tb_return_order_goods")
    @PreAuthorize("@el.check('tbReturnOrderGoods:edit')")
    public ResponseEntity update(@Validated @RequestBody TbReturnOrderGoodsDto resources){
        tbReturnOrderGoodsService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_return_order_goods")
    @ApiOperation("删除tb_return_order_goods")
    @PreAuthorize("@el.check('tbReturnOrderGoods:del')")
    public ResponseEntity delete(@RequestBody Set<String> ids) {
        tbReturnOrderGoodsService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出tb_return_order_goods")
    @ApiOperation("导出tb_return_order_goods")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbReturnOrderGoods:list')")
    public void download(HttpServletResponse response, TbReturnOrderGoodsQueryParam query) throws IOException {
        tbReturnOrderGoodsService.download(tbReturnOrderGoodsService.queryAll(query), response);
    }*/

}
