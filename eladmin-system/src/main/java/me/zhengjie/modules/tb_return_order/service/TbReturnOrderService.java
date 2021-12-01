package me.zhengjie.modules.tb_return_order.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_return_order.domain.TbReturnOrder;
import me.zhengjie.modules.tb_return_order.service.dto.TbReturnOrderDto;
import me.zhengjie.modules.tb_return_order.service.dto.TbReturnOrderQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbReturnOrderService extends CommonService<TbReturnOrder>  {

    static final String CACHE_KEY = "tbReturnOrder";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbReturnOrderDto>
    */
    PageInfo<TbReturnOrderDto> queryAll(TbReturnOrderQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbReturnOrderDto>
    */
    List<TbReturnOrderDto> queryAll(TbReturnOrderQueryParam query);

    TbReturnOrder getById(Long id);
    TbReturnOrderDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(TbReturnOrderDto resources);
    int updateById(TbReturnOrderDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbReturnOrderDto> all, HttpServletResponse response) throws IOException;
}
