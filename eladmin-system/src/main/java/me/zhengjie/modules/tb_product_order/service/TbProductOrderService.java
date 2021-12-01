package me.zhengjie.modules.tb_product_order.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_product_order.domain.TbProductOrder;
import me.zhengjie.modules.tb_product_order.service.dto.TbProductOrderDto;
import me.zhengjie.modules.tb_product_order.service.dto.TbProductOrderQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbProductOrderService extends CommonService<TbProductOrder>  {

    static final String CACHE_KEY = "tbProductOrder";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbProductOrderDto>
    */
    PageInfo<TbProductOrderDto> queryAll(TbProductOrderQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbProductOrderDto>
    */
    List<TbProductOrderDto> queryAll(TbProductOrderQueryParam query);

    TbProductOrder getById(Long id);
    TbProductOrderDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(TbProductOrderDto resources);
    int updateById(TbProductOrderDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbProductOrderDto> all, HttpServletResponse response) throws IOException;
}
