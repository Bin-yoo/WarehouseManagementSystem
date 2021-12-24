package me.zhengjie.modules.tb_wh_goods.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tb_wh_goods.domain.TbWhGoods;
import me.zhengjie.modules.tb_wh_goods.service.TbWhGoodsService;
import me.zhengjie.modules.tb_wh_goods.service.dto.TbWhGoodsDto;
import me.zhengjie.modules.tb_wh_goods.service.dto.TbWhGoodsQueryParam;
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
* @date 2021-12-21
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "tb_wh_goods管理")
@RequestMapping("/api/tbWhGoods")
public class TbWhGoodsController {

    private final TbWhGoodsService tbWhGoodsService;

    @GetMapping
    @Log("查询tb_wh_goods")
    @ApiOperation("查询tb_wh_goods")
    @PreAuthorize("@el.check('tbWhGoods:list')")
    public ResponseEntity query(TbWhGoodsQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbWhGoodsService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_wh_goods")
    @ApiOperation("新增tb_wh_goods")
    @PreAuthorize("@el.check('tbWhGoods:add')")
    public ResponseEntity create(@Validated @RequestBody TbWhGoodsDto resources){
        return new ResponseEntity<>(tbWhGoodsService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_wh_goods")
    @ApiOperation("修改tb_wh_goods")
    @PreAuthorize("@el.check('tbWhGoods:edit')")
    public ResponseEntity update(@Validated @RequestBody TbWhGoodsDto resources){
        tbWhGoodsService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_wh_goods")
    @ApiOperation("删除tb_wh_goods")
    @PreAuthorize("@el.check('tbWhGoods:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbWhGoodsService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出tb_wh_goods")
    @ApiOperation("导出tb_wh_goods")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbWhGoods:list')")
    public void download(HttpServletResponse response, TbWhGoodsQueryParam query) throws IOException {
        tbWhGoodsService.download(tbWhGoodsService.queryAll(query), response);
    }*/

}
