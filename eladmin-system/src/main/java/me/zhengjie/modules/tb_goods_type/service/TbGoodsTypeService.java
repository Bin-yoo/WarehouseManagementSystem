package me.zhengjie.modules.tb_goods_type.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.tb_goods_type.domain.TbGoodsType;
import me.zhengjie.modules.tb_goods_type.domain.vo.TbGoodsTypeTreeSelectVo;
import me.zhengjie.modules.tb_goods_type.domain.vo.TbGoodsTypeVo;
import me.zhengjie.modules.tb_goods_type.service.dto.TbGoodsTypeDto;
import me.zhengjie.modules.tb_goods_type.service.dto.TbGoodsTypeQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbGoodsTypeService extends CommonService<TbGoodsType>  {

    static final String CACHE_KEY = "tbGoodsType";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbGoodsTypeDto>
    */
    PageInfo<TbGoodsTypeDto> queryAll(TbGoodsTypeQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbGoodsTypeDto>
    */
    List<TbGoodsTypeDto> queryAll(TbGoodsTypeQueryParam query);

    List<TbGoodsTypeVo> queryAll(Long id);

    List<TbGoodsTypeTreeSelectVo> getTypesSelectTree(long pid);

    TbGoodsType getById(Long id);

    TbGoodsTypeDto findById(Long id);
    /**
     * 插入一条新数据。
     */
    int insert(TbGoodsTypeDto resources);
    int updateById(TbGoodsTypeDto resources);
    int removeById(Long id);

    int removeByIds(Set<Long> ids);

    TbGoodsTypeDto getGoodsTypeById(Long id);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbGoodsTypeDto> all, HttpServletResponse response) throws IOException;
}
