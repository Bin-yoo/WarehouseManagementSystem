package me.zhengjie.modules.tb_purchase_order.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_purchase_order.domain.TbPurchaseOrder;
import me.zhengjie.modules.tb_purchase_order.service.dto.TbPurchaseOrderDto;
import me.zhengjie.modules.tb_purchase_order.service.dto.TbPurchaseOrderQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbPurchaseOrderService extends CommonService<TbPurchaseOrder>  {

    static final String CACHE_KEY = "tbPurchaseOrder";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbPurchaseOrderDto>
    */
    PageInfo<TbPurchaseOrderDto> queryAll(TbPurchaseOrderQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbPurchaseOrderDto>
    */
    List<TbPurchaseOrderDto> queryAll(TbPurchaseOrderQueryParam query);

    TbPurchaseOrder getById(Long id);
    TbPurchaseOrderDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(TbPurchaseOrderDto resources);
    int updateById(TbPurchaseOrderDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbPurchaseOrderDto> all, HttpServletResponse response) throws IOException;
}
