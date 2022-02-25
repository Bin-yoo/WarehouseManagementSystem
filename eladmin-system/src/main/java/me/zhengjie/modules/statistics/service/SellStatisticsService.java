package me.zhengjie.modules.statistics.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.dto.SellStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.SellStatisticsQueryParam;
import org.springframework.data.domain.Pageable;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface SellStatisticsService extends CommonService<TbWhInventory>  {

    static final String CACHE_KEY = "tbWhInventory";

    /**
     * 查询数据分页
     * @param query 条件
     * @param pageable 分页参数
     * @return PageInfo<TbGoodsInfoDto>
     */
    PageInfo<SellStatisticsDto> queryAll(SellStatisticsQueryParam query, Pageable pageable);

    Object getDetail(Long goodId, Pageable pageable);
}
