package me.zhengjie.modules.tb_goods_unit.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tb_goods_unit.domain.TbGoodsUnit;
import me.zhengjie.modules.tb_goods_unit.service.TbGoodsUnitService;
import me.zhengjie.modules.tb_goods_unit.service.dto.TbGoodsUnitDto;
import me.zhengjie.modules.tb_goods_unit.service.dto.TbGoodsUnitQueryParam;
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
@Api(tags = "tb_goods_unit管理")
@RequestMapping("/api/tbGoodsUnit")
public class TbGoodsUnitController {

    private final TbGoodsUnitService tbGoodsUnitService;

    @GetMapping
    @Log("查询tb_goods_unit")
    @ApiOperation("查询tb_goods_unit")
    @PreAuthorize("@el.check('tbGoodsUnit:list')")
    public ResponseEntity query(TbGoodsUnitQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbGoodsUnitService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_goods_unit")
    @ApiOperation("新增tb_goods_unit")
    @PreAuthorize("@el.check('tbGoodsUnit:add')")
    public ResponseEntity create(@Validated @RequestBody TbGoodsUnitDto resources){
        return new ResponseEntity<>(tbGoodsUnitService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_goods_unit")
    @ApiOperation("修改tb_goods_unit")
    @PreAuthorize("@el.check('tbGoodsUnit:edit')")
    public ResponseEntity update(@Validated @RequestBody TbGoodsUnitDto resources){
        tbGoodsUnitService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_goods_unit")
    @ApiOperation("删除tb_goods_unit")
    @PreAuthorize("@el.check('tbGoodsUnit:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbGoodsUnitService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出tb_goods_unit")
    @ApiOperation("导出tb_goods_unit")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbGoodsUnit:list')")
    public void download(HttpServletResponse response, TbGoodsUnitQueryParam query) throws IOException {
        tbGoodsUnitService.download(tbGoodsUnitService.queryAll(query), response);
    }*/

}
