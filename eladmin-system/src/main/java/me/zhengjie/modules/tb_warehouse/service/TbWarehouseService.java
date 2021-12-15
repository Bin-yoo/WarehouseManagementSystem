package me.zhengjie.modules.tb_warehouse.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_warehouse.domain.TbWarehouse;
import me.zhengjie.modules.tb_warehouse.domain.vo.TbWarehouseVo;
import me.zhengjie.modules.tb_warehouse.service.dto.TbWarehouseDto;
import me.zhengjie.modules.tb_warehouse.service.dto.TbWarehouseQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbWarehouseService extends CommonService<TbWarehouse>  {

    static final String CACHE_KEY = "tbWarehouse";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbWarehouseDto>
    */
    PageInfo<TbWarehouseVo> queryAll(TbWarehouseQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbWarehouseDto>
    */
    List<TbWarehouseDto> queryAll(TbWarehouseQueryParam query);

    TbWarehouse getById(Long id);
    TbWarehouseDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(TbWarehouseDto resources);
    int updateById(TbWarehouseDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    Object getQueryParamAdviceList(String name);

    Object getEmpSelect();

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbWarehouseDto> all, HttpServletResponse response) throws IOException;
}
