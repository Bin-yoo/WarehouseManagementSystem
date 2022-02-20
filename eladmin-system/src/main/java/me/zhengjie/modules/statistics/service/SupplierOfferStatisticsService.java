package me.zhengjie.modules.statistics.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.base.PageInfo;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferStatisticsQueryParam;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface SupplierOfferStatisticsService extends CommonService<TbWhInventory>  {

    static final String CACHE_KEY = "tbWhInventory";

    /**
     * 查询数据分页
     * @param query 条件
     * @param pageable 分页参数
     * @return PageInfo<TbGoodsInfoDto>
     */
    PageInfo<SupplierOfferStatisticsDto> queryAll(SupplierOfferStatisticsQueryParam query, Pageable pageable);

    Object getDetail(Long sourceId, Pageable pageable);
}
