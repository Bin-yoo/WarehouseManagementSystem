package me.zhengjie.modules.tb_wh_goods.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_wh_goods.domain.TbWhGoods;
import me.zhengjie.modules.tb_wh_goods.service.dto.TbWhGoodsDto;
import me.zhengjie.modules.tb_wh_goods.service.dto.TbWhGoodsQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-21
*/
public interface TbWhGoodsService extends CommonService<TbWhGoods>  {

    static final String CACHE_KEY = "tbWhGoods";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbWhGoodsDto>
    */
    PageInfo<TbWhGoodsDto> queryAll(TbWhGoodsQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbWhGoodsDto>
    */
    List<TbWhGoodsDto> queryAll(TbWhGoodsQueryParam query);

    TbWhGoods getById(Long id);
    TbWhGoodsDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(TbWhGoodsDto resources);
    int updateById(TbWhGoodsDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbWhGoodsDto> all, HttpServletResponse response) throws IOException;
}
