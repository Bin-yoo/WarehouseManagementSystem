package me.zhengjie.modules.statistics.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.dto.CustomerSellStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.PurchaseStatisticsQueryParam;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferDetailDto;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferStatisticsDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Repository
public interface CustomerSellStatisticsMapper extends CommonMapper<TbWhInventory> {

    IPage<CustomerSellStatisticsDto> queryAll(IPage<CustomerSellStatisticsDto> queryPage, @Param("query") PurchaseStatisticsQueryParam query);

    IPage<SupplierOfferDetailDto> getDetail(IPage<SupplierOfferDetailDto> queryPage, @Param("sourceId") Long sourceId);

}
