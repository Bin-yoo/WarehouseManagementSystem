package me.zhengjie.modules.goodsunit.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.goodsunit.domain.TbGoodsUnit;
import me.zhengjie.modules.goodsunit.service.dto.TbGoodsUnitDto;
import me.zhengjie.modules.goodsunit.service.dto.TbGoodsUnitQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbGoodsUnitService extends CommonService<TbGoodsUnit>  {

    static final String CACHE_KEY = "tbGoodsUnit";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbGoodsUnitDto>
    */
    PageInfo<TbGoodsUnitDto> queryAll(TbGoodsUnitQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbGoodsUnitDto>
    */
    List<TbGoodsUnitDto> queryAll(TbGoodsUnitQueryParam query);

    TbGoodsUnit getById(Long id);
    TbGoodsUnitDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(TbGoodsUnitDto resources);
    int updateById(TbGoodsUnitDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbGoodsUnitDto> all, HttpServletResponse response) throws IOException;
}
