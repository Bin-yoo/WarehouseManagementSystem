package me.zhengjie.modules.tb_employee.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_employee.domain.TbEmployee;
import me.zhengjie.modules.tb_employee.domain.vo.TbEmployeeVo;
import me.zhengjie.modules.tb_employee.service.dto.TbEmployeeDto;
import me.zhengjie.modules.tb_employee.service.dto.TbEmployeeQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-08
*/
public interface TbEmployeeService extends CommonService<TbEmployee>  {

    static final String CACHE_KEY = "tbEmployee";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbEmployeeDto>
    */
    PageInfo<TbEmployeeVo> queryAll(TbEmployeeQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbEmployeeDto>
    */
    List<TbEmployeeVo> queryAll(TbEmployeeQueryParam query);

    TbEmployee getById(Long id);
    TbEmployeeDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(TbEmployeeDto resources);
    int updateById(TbEmployeeDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    Object getJobsSelect();

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbEmployeeDto> all, HttpServletResponse response) throws IOException;
}
