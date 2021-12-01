package me.zhengjie.modules.tb_return_order.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tb_return_order.domain.TbReturnOrder;
import me.zhengjie.modules.tb_return_order.service.TbReturnOrderService;
import me.zhengjie.modules.tb_return_order.service.dto.TbReturnOrderDto;
import me.zhengjie.modules.tb_return_order.service.dto.TbReturnOrderQueryParam;
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
@Api(tags = "tb_return_order管理")
@RequestMapping("/api/tbReturnOrder")
public class TbReturnOrderController {

    private final TbReturnOrderService tbReturnOrderService;

    @GetMapping
    @Log("查询tb_return_order")
    @ApiOperation("查询tb_return_order")
    @PreAuthorize("@el.check('tbReturnOrder:list')")
    public ResponseEntity query(TbReturnOrderQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbReturnOrderService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_return_order")
    @ApiOperation("新增tb_return_order")
    @PreAuthorize("@el.check('tbReturnOrder:add')")
    public ResponseEntity create(@Validated @RequestBody TbReturnOrderDto resources){
        return new ResponseEntity<>(tbReturnOrderService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_return_order")
    @ApiOperation("修改tb_return_order")
    @PreAuthorize("@el.check('tbReturnOrder:edit')")
    public ResponseEntity update(@Validated @RequestBody TbReturnOrderDto resources){
        tbReturnOrderService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_return_order")
    @ApiOperation("删除tb_return_order")
    @PreAuthorize("@el.check('tbReturnOrder:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbReturnOrderService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出tb_return_order")
    @ApiOperation("导出tb_return_order")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbReturnOrder:list')")
    public void download(HttpServletResponse response, TbReturnOrderQueryParam query) throws IOException {
        tbReturnOrderService.download(tbReturnOrderService.queryAll(query), response);
    }*/

}
