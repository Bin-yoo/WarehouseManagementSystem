package me.zhengjie.modules.inventory.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.inventory.service.TbWhInventoryService;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryDto;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryQueryParam;
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
@Api(tags = "tb_wh_inventory管理")
@RequestMapping("/api/tbWhInventory")
public class TbWhInventoryController {

    private final TbWhInventoryService tbWhInventoryService;

    @GetMapping
    @Log("查询tb_wh_inventory")
    @ApiOperation("查询tb_wh_inventory")
    @PreAuthorize("@el.check('tbWhInventory:list')")
    public ResponseEntity query(TbWhInventoryQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbWhInventoryService.queryAll(query,pageable),HttpStatus.OK);
    }

    /*@PostMapping
    @Log("新增tb_wh_inventory")
    @ApiOperation("新增tb_wh_inventory")
    @PreAuthorize("@el.check('tbWhInventory:add')")
    public ResponseEntity create(@Validated @RequestBody TbWhInventoryDto resources){
        return new ResponseEntity<>(tbWhInventoryService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_wh_inventory")
    @ApiOperation("修改tb_wh_inventory")
    @PreAuthorize("@el.check('tbWhInventory:edit')")
    public ResponseEntity update(@Validated @RequestBody TbWhInventoryDto resources){
        tbWhInventoryService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_wh_inventory")
    @ApiOperation("删除tb_wh_inventory")
    @PreAuthorize("@el.check('tbWhInventory:del')")
    public ResponseEntity delete(@RequestBody Set<String> ids) {
        tbWhInventoryService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

    @GetMapping("/getWareHouseSelect")
    @Log("获取仓库列表下拉")
    @ApiOperation("获取仓库列表下拉")
    @PreAuthorize("@el.check('tbWhInventory:list')")
    public ResponseEntity getWareHouseSelect(){
        return new ResponseEntity<>(tbWhInventoryService.getWareHouseSelect(),HttpStatus.OK);
    }

    @GetMapping("/getWhInOutDetail")
    @Log("获取仓库库存出入详情")
    @ApiOperation("获取仓库列表下拉")
    @PreAuthorize("@el.check('tbWhInventory:list')")
    public ResponseEntity getWhInOutDetail(){
        return new ResponseEntity<>(tbWhInventoryService.getWhInOutDetail(),HttpStatus.OK);
    }

    /*
    @Log("导出tb_wh_inventory")
    @ApiOperation("导出tb_wh_inventory")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbWhInventory:list')")
    public void download(HttpServletResponse response, TbWhInventoryQueryParam query) throws IOException {
        tbWhInventoryService.download(tbWhInventoryService.queryAll(query), response);
    }*/

}
