package me.zhengjie.modules.orders.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.orders.domain.TbOrders;
import me.zhengjie.modules.orders.service.dto.TbOrdersDto;
import me.zhengjie.modules.orders.service.dto.TbOrdersQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2022-01-03
*/
public interface TbOrdersService extends CommonService<TbOrders>  {

    static final String CACHE_KEY = "tbOrders";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbOrdersDto>
    */
    PageInfo<TbOrdersDto> queryAll(TbOrdersQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbOrdersDto>
    */
    List<TbOrdersDto> queryAll(TbOrdersQueryParam query);

    TbOrders getById(Long id);
    TbOrdersDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(TbOrdersDto resources);
    int updateById(TbOrdersDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbOrdersDto> all, HttpServletResponse response) throws IOException;
}
