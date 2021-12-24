package me.zhengjie.modules.employee.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.employee.service.TbEmployeeService;
import me.zhengjie.modules.employee.service.dto.TbEmployeeDto;
import me.zhengjie.modules.employee.service.dto.TbEmployeeQueryParam;
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
* @date 2021-12-08
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "tb_employee管理")
@RequestMapping("/api/tbEmployee")
public class TbEmployeeController {

    private final TbEmployeeService tbEmployeeService;

    @GetMapping
    @Log("查询tb_employee")
    @ApiOperation("查询tb_employee")
    @PreAuthorize("@el.check('tbEmployee:list')")
    public ResponseEntity query(TbEmployeeQueryParam query, Pageable pageable){
        return new ResponseEntity<>(tbEmployeeService.queryAll(query,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增tb_employee")
    @ApiOperation("新增tb_employee")
    @PreAuthorize("@el.check('tbEmployee:add')")
    public ResponseEntity create(@Validated @RequestBody TbEmployeeDto resources){
        return new ResponseEntity<>(tbEmployeeService.insert(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改tb_employee")
    @ApiOperation("修改tb_employee")
    @PreAuthorize("@el.check('tbEmployee:edit')")
    public ResponseEntity update(@Validated @RequestBody TbEmployeeDto resources){
        tbEmployeeService.updateById(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除tb_employee")
    @ApiOperation("删除tb_employee")
    @PreAuthorize("@el.check('tbEmployee:del')")
    public ResponseEntity delete(@RequestBody Set<Long> ids) {
        tbEmployeeService.removeByIds(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getJobsSelect")
    @Log("获取岗位下拉列表数据")
    @ApiOperation("获取岗位下拉列表数据")
    @PreAuthorize("@el.check('tbEmployee:add', 'tbEmployee:edit')")
    public ResponseEntity getJobsSelect() {
        return new ResponseEntity<>(tbEmployeeService.getJobsSelect(), HttpStatus.OK);
    }

    /*
    @Log("导出tb_employee")
    @ApiOperation("导出tb_employee")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('tbEmployee:list')")
    public void download(HttpServletResponse response, TbEmployeeQueryParam query) throws IOException {
        tbEmployeeService.download(tbEmployeeService.queryAll(query), response);
    }*/

}
