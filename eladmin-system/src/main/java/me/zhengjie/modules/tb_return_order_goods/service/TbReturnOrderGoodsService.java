package me.zhengjie.modules.tb_return_order_goods.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_return_order_goods.domain.TbReturnOrderGoods;
import me.zhengjie.modules.tb_return_order_goods.service.dto.TbReturnOrderGoodsDto;
import me.zhengjie.modules.tb_return_order_goods.service.dto.TbReturnOrderGoodsQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbReturnOrderGoodsService extends CommonService<TbReturnOrderGoods>  {

    static final String CACHE_KEY = "tbReturnOrderGoods";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbReturnOrderGoodsDto>
    */
    PageInfo<TbReturnOrderGoodsDto> queryAll(TbReturnOrderGoodsQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbReturnOrderGoodsDto>
    */
    List<TbReturnOrderGoodsDto> queryAll(TbReturnOrderGoodsQueryParam query);

    TbReturnOrderGoods getById(String id);
    TbReturnOrderGoodsDto findById(String id);

    /**
     * 插入一条新数据。
     */
    int insert(TbReturnOrderGoodsDto resources);
    int updateById(TbReturnOrderGoodsDto resources);
    int removeById(String id);
    int removeByIds(Set<String> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbReturnOrderGoodsDto> all, HttpServletResponse response) throws IOException;
}
