package me.zhengjie.modules.statistics.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.dto.DeptProductStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.ProductStatisticsQueryParam;
import org.springframework.data.domain.Pageable;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface DeptProductStatisticsService extends CommonService<TbWhInventory>  {

    static final String CACHE_KEY = "tbWhInventory";

    /**
     * 查询数据分页
     * @param query 条件
     * @param pageable 分页参数
     * @return PageInfo<TbGoodsInfoDto>
     */
    PageInfo<DeptProductStatisticsDto> queryAll(ProductStatisticsQueryParam query, Pageable pageable);

    Object getDetail(Long sourceId, Pageable pageable);
}
