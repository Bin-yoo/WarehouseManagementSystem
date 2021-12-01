package me.zhengjie.modules.tb_goods_info.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_goods_info.domain.TbGoodsInfo;
import me.zhengjie.modules.tb_goods_info.service.dto.TbGoodsInfoDto;
import me.zhengjie.modules.tb_goods_info.service.dto.TbGoodsInfoQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbGoodsInfoService extends CommonService<TbGoodsInfo>  {

    static final String CACHE_KEY = "tbGoodsInfo";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbGoodsInfoDto>
    */
    PageInfo<TbGoodsInfoDto> queryAll(TbGoodsInfoQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbGoodsInfoDto>
    */
    List<TbGoodsInfoDto> queryAll(TbGoodsInfoQueryParam query);

    TbGoodsInfo getById(Long id);
    TbGoodsInfoDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(TbGoodsInfoDto resources);
    int updateById(TbGoodsInfoDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbGoodsInfoDto> all, HttpServletResponse response) throws IOException;
}
