package me.zhengjie.modules.tb_product_order.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.tb_product_order.domain.TbProductOrder;
import me.zhengjie.modules.tb_product_order.service.TbProductOrderService;
import me.zhengjie.modules.tb_product_order.service.dto.TbProductOrderDto;
import me.zhengjie.modules.tb_product_order.service.dto.TbProductOrderQueryParam;
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
@Api(tags = "tb_product_order管理")
@RequestMapping("/api/tbProductOrder")
public class TbProductOrderController {

    private final TbProductOrderService tbProductOrderService;

    @GetMapping
    @Log("查询tb_product_order")
    @ApiOperation("查询tb_product_order")
    @PreAuthorize("@el.check('tbProductOrder:list')")
    public ResponseEntity query(TbProductOrderQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbProductOrderService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_product_order")
    @ApiOperation("新增tb_product_order")
    @PreAuthorize("@el.check('tbProductOrder:add')")
    public ResponseEntity create(@Validated @RequestBody TbProductOrderDto resources){
        return new ResponseEntity<>(tbProductOrderService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_product_order")
    @ApiOperation("修改tb_product_order")
    @PreAuthorize("@el.check('tbProductOrder:edit')")
    public ResponseEntity update(@Validated @RequestBody TbProductOrderDto resources){
        tbProductOrderService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_product_order")
    @ApiOperation("删除tb_product_order")
    @PreAuthorize("@el.check('tbProductOrder:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbProductOrderService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*
    @Log("导出tb_product_order")
    @ApiOperation("导出tb_product_order")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbProductOrder:list')")
    public void download(HttpServletResponse response, TbProductOrderQueryParam query) throws IOException {
        tbProductOrderService.download(tbProductOrderService.queryAll(query), response);
    }*/

}
