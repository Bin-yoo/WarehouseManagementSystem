package me.zhengjie.modules.homepage.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import me.zhengjie.annotation.Log;
import me.zhengjie.modules.homepage.service.HomePageService;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryQueryParam;
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
@Api(tags = "首页数据展示")
@RequestMapping("/api/homePage")
public class HomePageController {

    private final HomePageService homePageService;

    @GetMapping("/queryData")
    @Log("快捷方式数据")
    @ApiOperation("快捷方式数据")
    @PreAuthorize("@el.check('homePage:list')")
    public ResponseEntity queryData(){
        return new ResponseEntity<>(homePageService.queryData(),HttpStatus.OK);
    }

    @GetMapping("/queryLineBarData")
    @Log("折线图数据")
    @ApiOperation("快捷方式数据")
    @PreAuthorize("@el.check('homePage:list')")
    public ResponseEntity queryLineBarData(){
        return new ResponseEntity<>(homePageService.queryLineBarData(),HttpStatus.OK);
    }

}
