package me.zhengjie.modules.tb_product_order_goods.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_product_order_goods.domain.TbProductOrderGoods;
import me.zhengjie.modules.tb_product_order_goods.service.dto.TbProductOrderGoodsDto;
import me.zhengjie.modules.tb_product_order_goods.service.dto.TbProductOrderGoodsQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbProductOrderGoodsService extends CommonService<TbProductOrderGoods>  {

    static final String CACHE_KEY = "tbProductOrderGoods";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbProductOrderGoodsDto>
    */
    PageInfo<TbProductOrderGoodsDto> queryAll(TbProductOrderGoodsQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbProductOrderGoodsDto>
    */
    List<TbProductOrderGoodsDto> queryAll(TbProductOrderGoodsQueryParam query);

    TbProductOrderGoods getById(String id);
    TbProductOrderGoodsDto findById(String id);

    /**
     * 插入一条新数据。
     */
    int insert(TbProductOrderGoodsDto resources);
    int updateById(TbProductOrderGoodsDto resources);
    int removeById(String id);
    int removeByIds(Set<String> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbProductOrderGoodsDto> all, HttpServletResponse response) throws IOException;
}
