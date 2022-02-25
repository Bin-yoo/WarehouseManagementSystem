package me.zhengjie.modules.statistics.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.dto.PurchaseStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferDetailDto;
import me.zhengjie.modules.statistics.service.dto.PurchaseStatisticsQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Repository
public interface PurchaseStatisticsMapper extends CommonMapper<TbWhInventory> {

    IPage<PurchaseStatisticsDto> queryAll(IPage<PurchaseStatisticsDto> queryPage, @Param("query") PurchaseStatisticsQueryParam query);

    IPage<SupplierOfferDetailDto> getDetail(@Param("goodId") Long goodId, IPage<SupplierOfferDetailDto> queryPage);

}
