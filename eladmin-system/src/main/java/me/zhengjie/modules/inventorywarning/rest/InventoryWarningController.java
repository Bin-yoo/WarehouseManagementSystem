package me.zhengjie.modules.inventorywarning.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryQueryParam;
import me.zhengjie.modules.inventorywarning.service.InventoryWarningService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
* @author LiangBin
* @date 2021-12-01
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "低库存报警管理")
@RequestMapping("/api/inventoryWarning")
public class InventoryWarningController {

    private final InventoryWarningService inventoryWarningService;

    @GetMapping
    @Log("查询低库存报警列表")
    @ApiOperation("查询低库存报警列表")
    @PreAuthorize("@el.check('inventoryWarning:list')")
    public ResponseEntity query(TbWhInventoryQueryParam query, Pageable pageable){
        return new ResponseEntity<>(inventoryWarningService.queryAll(query,pageable),HttpStatus.OK);
    }

    /*
    @Log("导出低库存报警")
    @ApiOperation("导出低库存报警")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('inventoryWarning:list')")
    public void download(HttpServletResponse response, TbWhInventoryQueryParam query) throws IOException {
        inventoryWarningService.download(inventoryWarningService.queryAll(query), response);
    }*/

}
