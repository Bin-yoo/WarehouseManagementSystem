package me.zhengjie.modules.tb_order_goods.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_order_goods.domain.TbOrderGoods;
import me.zhengjie.modules.tb_order_goods.service.dto.TbOrderGoodsDto;
import me.zhengjie.modules.tb_order_goods.service.dto.TbOrderGoodsQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2022-02-10
*/
public interface TbOrderGoodsService extends CommonService<TbOrderGoods>  {

    static final String CACHE_KEY = "tbOrderGoods";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbOrderGoodsDto>
    */
    PageInfo<TbOrderGoodsDto> queryAll(TbOrderGoodsQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbOrderGoodsDto>
    */
    List<TbOrderGoodsDto> queryAll(TbOrderGoodsQueryParam query);

    TbOrderGoods getById(Long id);
    TbOrderGoodsDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(TbOrderGoodsDto resources);
    int updateById(TbOrderGoodsDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbOrderGoodsDto> all, HttpServletResponse response) throws IOException;
}
