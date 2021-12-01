package me.zhengjie.modules.tb_goods_info.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tb_goods_info.domain.TbGoodsInfo;
import me.zhengjie.modules.tb_goods_info.service.TbGoodsInfoService;
import me.zhengjie.modules.tb_goods_info.service.dto.TbGoodsInfoDto;
import me.zhengjie.modules.tb_goods_info.service.dto.TbGoodsInfoQueryParam;
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
@Api(tags = "tb_goods_info管理")
@RequestMapping("/api/tbGoodsInfo")
public class TbGoodsInfoController {

    private final TbGoodsInfoService tbGoodsInfoService;

    @GetMapping
    @Log("查询tb_goods_info")
    @ApiOperation("查询tb_goods_info")
    @PreAuthorize("@el.check('tbGoodsInfo:list')")
    public ResponseEntity query(TbGoodsInfoQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbGoodsInfoService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_goods_info")
    @ApiOperation("新增tb_goods_info")
    @PreAuthorize("@el.check('tbGoodsInfo:add')")
    public ResponseEntity create(@Validated @RequestBody TbGoodsInfoDto resources){
        return new ResponseEntity<>(tbGoodsInfoService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_goods_info")
    @ApiOperation("修改tb_goods_info")
    @PreAuthorize("@el.check('tbGoodsInfo:edit')")
    public ResponseEntity update(@Validated @RequestBody TbGoodsInfoDto resources){
        tbGoodsInfoService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_goods_info")
    @ApiOperation("删除tb_goods_info")
    @PreAuthorize("@el.check('tbGoodsInfo:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbGoodsInfoService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出tb_goods_info")
    @ApiOperation("导出tb_goods_info")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbGoodsInfo:list')")
    public void download(HttpServletResponse response, TbGoodsInfoQueryParam query) throws IOException {
        tbGoodsInfoService.download(tbGoodsInfoService.queryAll(query), response);
    }*/

}
