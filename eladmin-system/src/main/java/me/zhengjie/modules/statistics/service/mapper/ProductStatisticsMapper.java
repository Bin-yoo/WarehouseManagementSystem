package me.zhengjie.modules.statistics.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.dto.ProductStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.ProductStatisticsQueryParam;
import me.zhengjie.modules.statistics.service.dto.PurchaseStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferDetailDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Repository
public interface ProductStatisticsMapper extends CommonMapper<TbWhInventory> {

    IPage<ProductStatisticsDto> queryAll(IPage<ProductStatisticsDto> queryPage, @Param("query") ProductStatisticsQueryParam query);

    IPage<SupplierOfferDetailDto> getDetail(@Param("goodId") Long goodId, IPage<SupplierOfferDetailDto> queryPage);

}
