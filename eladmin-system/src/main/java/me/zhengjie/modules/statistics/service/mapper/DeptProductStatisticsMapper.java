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
public interface DeptProductStatisticsMapper extends CommonMapper<TbWhInventory> {

    IPage<DeptProductStatisticsDto> queryAll(IPage<DeptProductStatisticsDto> queryPage, @Param("query") ProductStatisticsQueryParam query);

    IPage<SupplierOfferDetailDto> getDetail(IPage<SupplierOfferDetailDto> queryPage, @Param("sourceId") Long sourceId);

}
