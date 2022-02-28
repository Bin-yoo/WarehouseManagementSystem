package me.zhengjie.modules.statistics.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.dto.ProductStatisticsQueryParam;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferDetailDto;
import me.zhengjie.modules.statistics.service.dto.TransferStatisticsDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Repository
public interface TransferStatisticsMapper extends CommonMapper<TbWhInventory> {

    IPage<TransferStatisticsDto> queryAll(IPage<TransferStatisticsDto> queryPage, @Param("query") ProductStatisticsQueryParam query);

    IPage<SupplierOfferDetailDto> getDetail(@Param("goodId") Long goodId, IPage<SupplierOfferDetailDto> queryPage);

}
