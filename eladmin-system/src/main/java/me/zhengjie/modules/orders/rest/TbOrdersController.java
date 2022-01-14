package me.zhengjie.modules.orders.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.orders.service.TbOrdersService;
import me.zhengjie.modules.orders.service.dto.TbOrdersDto;
import me.zhengjie.modules.orders.service.dto.TbOrdersQueryParam;
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
* @date 2022-01-03
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "单据管理")
@RequestMapping("/api/tbOrders")
public class TbOrdersController {

    private final TbOrdersService tbOrdersService;

    @GetMapping
    @Log("查询单据")
    @ApiOperation("查询单据")
    @PreAuthorize("@el.check('tbOrders:list')")
    public ResponseEntity query(TbOrdersQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbOrdersService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增单据")
    @ApiOperation("新增单据")
    @PreAuthorize("@el.check('tbOrders:add')")
    public ResponseEntity create(@Validated @RequestBody TbOrdersDto resources){
        return new ResponseEntity<>(tbOrdersService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改单据")
    @ApiOperation("修改单据")
    @PreAuthorize("@el.check('tbOrders:edit')")
    public ResponseEntity update(@Validated @RequestBody TbOrdersDto resources){
        tbOrdersService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除单据")
    @ApiOperation("删除单据")
    @PreAuthorize("@el.check('tbOrders:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbOrdersService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出单据")
    @ApiOperation("导出单据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbOrders:list')")
    public void download(HttpServletResponse response, TbOrdersQueryParam query) throws IOException {
        tbOrdersService.download(tbOrdersService.queryAll(query), response);
    }*/

}
