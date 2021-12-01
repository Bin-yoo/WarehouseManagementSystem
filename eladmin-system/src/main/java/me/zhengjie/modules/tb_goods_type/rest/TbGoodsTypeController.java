package me.zhengjie.modules.tb_goods_type.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tb_goods_type.domain.TbGoodsType;
import me.zhengjie.modules.tb_goods_type.service.TbGoodsTypeService;
import me.zhengjie.modules.tb_goods_type.service.dto.TbGoodsTypeDto;
import me.zhengjie.modules.tb_goods_type.service.dto.TbGoodsTypeQueryParam;
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
@Api(tags = "tb_goods_type管理")
@RequestMapping("/api/tbGoodsType")
public class TbGoodsTypeController {

    private final TbGoodsTypeService tbGoodsTypeService;

    @GetMapping
    @Log("查询tb_goods_type")
    @ApiOperation("查询tb_goods_type")
    @PreAuthorize("@el.check('tbGoodsType:list')")
    public ResponseEntity query(TbGoodsTypeQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbGoodsTypeService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_goods_type")
    @ApiOperation("新增tb_goods_type")
    @PreAuthorize("@el.check('tbGoodsType:add')")
    public ResponseEntity create(@Validated @RequestBody TbGoodsTypeDto resources){
        return new ResponseEntity<>(tbGoodsTypeService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_goods_type")
    @ApiOperation("修改tb_goods_type")
    @PreAuthorize("@el.check('tbGoodsType:edit')")
    public ResponseEntity update(@Validated @RequestBody TbGoodsTypeDto resources){
        tbGoodsTypeService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_goods_type")
    @ApiOperation("删除tb_goods_type")
    @PreAuthorize("@el.check('tbGoodsType:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbGoodsTypeService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出tb_goods_type")
    @ApiOperation("导出tb_goods_type")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbGoodsType:list')")
    public void download(HttpServletResponse response, TbGoodsTypeQueryParam query) throws IOException {
        tbGoodsTypeService.download(tbGoodsTypeService.queryAll(query), response);
    }*/

}
