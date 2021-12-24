package me.zhengjie.modules.warehouse.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.warehouse.service.TbWarehouseService;
import me.zhengjie.modules.warehouse.service.dto.TbWarehouseDto;
import me.zhengjie.modules.warehouse.service.dto.TbWarehouseQueryParam;
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
@Api(tags = "tb_warehouse管理")
@RequestMapping("/api/tbWarehouse")
public class TbWarehouseController {

    private final TbWarehouseService tbWarehouseService;

    @GetMapping
    @Log("查询tb_warehouse")
    @ApiOperation("查询tb_warehouse")
    @PreAuthorize("@el.check('tbWarehouse:list')")
    public ResponseEntity query(TbWarehouseQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbWarehouseService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_warehouse")
    @ApiOperation("新增tb_warehouse")
    @PreAuthorize("@el.check('tbWarehouse:add')")
    public ResponseEntity create(@Validated @RequestBody TbWarehouseDto resources){
        return new ResponseEntity<>(tbWarehouseService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_warehouse")
    @ApiOperation("修改tb_warehouse")
    @PreAuthorize("@el.check('tbWarehouse:edit')")
    public ResponseEntity update(@Validated @RequestBody TbWarehouseDto resources){
        tbWarehouseService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_warehouse")
    @ApiOperation("删除tb_warehouse")
    @PreAuthorize("@el.check('tbWarehouse:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbWarehouseService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getQueryParamAdviceList")
    @Log("获取条件输入框输入建议")
    @ApiOperation("获取条件输入框输入建议")
    @PreAuthorize("@el.check('tbWarehouse:list')")
    public ResponseEntity getQueryParamAdviceList(String name) {
        return new ResponseEntity<>(tbWarehouseService.getQueryParamAdviceList(name), HttpStatus.OK);
    }

    @GetMapping("/getEmpSelect")
    @Log("获取员工信息下拉数据")
    @ApiOperation("获取员工信息下拉数据")
    @PreAuthorize("@el.check('tbWarehouse:add', 'tbWarehouse:edit')")
    public ResponseEntity getEmpSelect() {
        return new ResponseEntity<>(tbWarehouseService.getEmpSelect(), HttpStatus.OK);
    }

    /*
    @Log("导出tb_warehouse")
    @ApiOperation("导出tb_warehouse")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbWarehouse:list')")
    public void download(HttpServletResponse response, TbWarehouseQueryParam query) throws IOException {
        tbWarehouseService.download(tbWarehouseService.queryAll(query), response);
    }*/

}
