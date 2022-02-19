package me.zhengjie.modules.inventoryStatistics.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.inventoryStatistics.service.InventoryStatisticsService;
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
@Api(tags = "首页数据展示")
@RequestMapping("/api/inventoryStatistics")
public class InventoryStatisticsController {

    private final InventoryStatisticsService inventoryStatisticsService;

    @GetMapping("/queryLineBarData")
    @Log("折线图数据")
    @ApiOperation("折线图数据")
    @PreAuthorize("@el.check('inventoryStatistics:list')")
    public ResponseEntity queryLineBarData(){
        return new ResponseEntity<>(inventoryStatisticsService.queryLineBarData(),HttpStatus.OK);
    }

    @GetMapping("/queryPieChartData")
    @Log("饼状图数据")
    @ApiOperation("饼状图数据")
    @PreAuthorize("@el.check('inventoryStatistics:list')")
    public ResponseEntity queryPieChartData(){
        return new ResponseEntity<>(inventoryStatisticsService.queryPieChartData(),HttpStatus.OK);
    }
}
