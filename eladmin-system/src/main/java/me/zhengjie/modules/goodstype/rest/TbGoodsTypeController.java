package me.zhengjie.modules.goodstype.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.goodstype.service.TbGoodsTypeService;
import me.zhengjie.modules.goodstype.service.dto.TbGoodsTypeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;

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

    /*@GetMapping
    @Log("查询tb_goods_type")
    @ApiOperation("查询tb_goods_type")
    @PreAuthorize("@el.check('tbGoodsType:list')")
    public ResponseEntity query(TbGoodsTypeQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbGoodsTypeService.queryAll(query,pageable),HttpStatus.OK);
    }*/

    @GetMapping
    @Log("查询tb_goods_type")
    @ApiOperation("查询tb_goods_type")
    @PreAuthorize("@el.check('tbGoodsType:list')")
    public ResponseEntity query(){
        return new ResponseEntity<>(tbGoodsTypeService.queryAll(0l),HttpStatus.OK);
    }

    @GetMapping("/getTypesFolderSelectTree")
    @Log("查询tb_goods_type树形选择数据")
    @ApiOperation("查询tb_goods_type树形选择数据")
    @PreAuthorize("@el.check('tbGoodsType:add','tbGoodsType:edit')")
    public ResponseEntity getTypesFolderSelectTree(){
        return new ResponseEntity<>(tbGoodsTypeService.getTypesFolderSelectTree(0l),HttpStatus.OK);
    }

    @GetMapping("/getTypesSelectTree")
    @Log("查询tb_goods_type树形选择数据")
    @ApiOperation("查询tb_goods_type树形选择数据")
    @PreAuthorize("@el.check('tbGoodsType:add','tbGoodsType:edit')")
    public ResponseEntity getTypesSelectTree(){
        return new ResponseEntity<>(tbGoodsTypeService.getTypesSelectTree(0l),HttpStatus.OK);
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

    @GetMapping("/getGoodsTypeById")
    @Log("获取货品类型信息")
    @ApiOperation("获取货品类型信息")
    @PreAuthorize("@el.check('tbGoodsType:edit')")
    public ResponseEntity getGoodsTypeById(Long id) {
        return new ResponseEntity<>(tbGoodsTypeService.getGoodsTypeById(id), HttpStatus.OK);
    }

    @DeleteMapping
    @Log("删除tb_goods_type")
    @ApiOperation("删除tb_goods_type")
    @PreAuthorize("@el.check('tbGoodsType:del')")
    public ResponseEntity delete(@RequestBody Long id) {
        // 验证是否还有下级分类
        tbGoodsTypeService.verification(id);
        tbGoodsTypeService.removeById(id);
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
