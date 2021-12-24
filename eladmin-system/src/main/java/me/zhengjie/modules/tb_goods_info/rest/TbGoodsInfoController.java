package me.zhengjie.modules.tb_goods_info.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.domain.LocalStorage;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.tb_goods_info.domain.vo.TbGoodsInfoVo;
import me.zhengjie.modules.tb_goods_info.service.TbGoodsInfoService;
import me.zhengjie.modules.tb_goods_info.service.dto.TbGoodsInfoDto;
import me.zhengjie.modules.tb_goods_info.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.tb_goods_type.service.TbGoodsTypeService;
import me.zhengjie.utils.FileUtil;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-21
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "tb_goods_info管理")
@RequestMapping("/api/tbGoodsInfo")
public class TbGoodsInfoController {

    private final TbGoodsInfoService tbGoodsInfoService;
    private final TbGoodsTypeService tbGoodsTypeService;

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
    public ResponseEntity create(@Validated @RequestBody TbGoodsInfoVo resources){
        return new ResponseEntity<>(tbGoodsInfoService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_goods_info")
    @ApiOperation("修改tb_goods_info")
    @PreAuthorize("@el.check('tbGoodsInfo:edit')")
    public ResponseEntity update(@Validated @RequestBody TbGoodsInfoVo resources){
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

    @GetMapping("/getGoodCode")
    @Log("获取新的货品编码")
    @ApiOperation("获取新的货品编码")
    @PreAuthorize("@el.check('tbGoodsInfo:add', 'tbGoodsInfo:edit')")
    public ResponseEntity getGoodCode(Long typeId){
        return new ResponseEntity<>(tbGoodsInfoService.getGoodCode(typeId),HttpStatus.OK);
    }

    @GetMapping("/getTypesSelectTree")
    @Log("查询tb_goods_type树形选择数据")
    @ApiOperation("查询tb_goods_type树形选择数据")
    @PreAuthorize("@el.check('tbGoodsType:add','tbGoodsType:edit')")
    public ResponseEntity getTypesSelectTree(){
        return new ResponseEntity<>(tbGoodsTypeService.getTypesSelectTree(1l),HttpStatus.OK);
    }

    @GetMapping("/getGoodUnitsSelect")
    @Log("查询tb_goods_units下拉选择数据")
    @ApiOperation("查询tb_goods_units下拉选择数据")
    @PreAuthorize("@el.check('tbGoodsType:add','tbGoodsType:edit')")
    public ResponseEntity getGoodUnitsSelect(){
        return new ResponseEntity<>(tbGoodsInfoService.getGoodUnitsSelect(),HttpStatus.OK);
    }

    @GetMapping("/getWareHouseGoodsInfo")
    @Log("查询库存信息数据")
    @ApiOperation("查询库存信息数据")
    @PreAuthorize("@el.check('tbGoodsType:add','tbGoodsType:edit')")
    public ResponseEntity getWareHouseGoodsInfo(){
        return new ResponseEntity<>(tbGoodsInfoService.getWareHouseGoodsInfo(),HttpStatus.OK);
    }

    @GetMapping("/getWareHouseGoodsInfoByGoodId")
    @Log("根据货品id查询货品库存信息数据")
    @ApiOperation("查询货品库存信息数据")
    @PreAuthorize("@el.check('tbGoodsType:add','tbGoodsType:edit')")
    public ResponseEntity getWareHouseGoodsInfoByGoodId(Long goodId){
        return new ResponseEntity<>(tbGoodsInfoService.getWareHouseGoodsInfoByGoodId(goodId),HttpStatus.OK);
    }

    @PostMapping("/uploadGoodPic")
    @Log("上传货品图片")
    @ApiOperation("上传货品图片")
    @PreAuthorize("@el.check('tbGoodsType:add','tbGoodsType:edit')")
    public ResponseEntity<Object> uploadGoodPic(@RequestParam MultipartFile file) throws IOException {
        // 判断文件是否为图片
        String suffix = FileUtil.getExtensionName(file.getOriginalFilename());
        if(!FileUtil.IMAGE.equals(FileUtil.getFileType(suffix))){
            throw new BadRequestException("只能上传图片");
        }
        return new ResponseEntity<>(tbGoodsInfoService.uploadGoodPic(file), HttpStatus.OK);
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
