package me.zhengjie.modules.tb_purchase_order_goods.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_purchase_order_goods.domain.TbPurchaseOrderGoods;
import me.zhengjie.modules.tb_purchase_order_goods.service.dto.TbPurchaseOrderGoodsDto;
import me.zhengjie.modules.tb_purchase_order_goods.service.dto.TbPurchaseOrderGoodsQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbPurchaseOrderGoodsService extends CommonService<TbPurchaseOrderGoods>  {

    static final String CACHE_KEY = "tbPurchaseOrderGoods";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbPurchaseOrderGoodsDto>
    */
    PageInfo<TbPurchaseOrderGoodsDto> queryAll(TbPurchaseOrderGoodsQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbPurchaseOrderGoodsDto>
    */
    List<TbPurchaseOrderGoodsDto> queryAll(TbPurchaseOrderGoodsQueryParam query);

    TbPurchaseOrderGoods getById(String id);
    TbPurchaseOrderGoodsDto findById(String id);

    /**
     * 插入一条新数据。
     */
    int insert(TbPurchaseOrderGoodsDto resources);
    int updateById(TbPurchaseOrderGoodsDto resources);
    int removeById(String id);
    int removeByIds(Set<String> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbPurchaseOrderGoodsDto> all, HttpServletResponse response) throws IOException;
}
