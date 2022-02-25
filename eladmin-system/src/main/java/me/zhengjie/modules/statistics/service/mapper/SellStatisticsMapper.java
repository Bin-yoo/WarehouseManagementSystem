package me.zhengjie.modules.statistics.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.dto.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Repository
public interface SellStatisticsMapper extends CommonMapper<TbWhInventory> {

    IPage<SellStatisticsDto> queryAll(IPage<SellStatisticsDto> queryPage, @Param("query") SellStatisticsQueryParam query);

    IPage<SupplierOfferDetailDto> getDetail(@Param("goodId") Long goodId, IPage<SupplierOfferDetailDto> queryPage);

}
