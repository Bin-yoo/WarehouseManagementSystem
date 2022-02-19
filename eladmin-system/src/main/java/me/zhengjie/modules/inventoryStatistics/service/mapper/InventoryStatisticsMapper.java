package me.zhengjie.modules.inventoryStatistics.service.mapper;

import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventoryStatistics.service.dto.LineBarDto;
import me.zhengjie.modules.inventoryStatistics.service.dto.PieChartDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Repository
public interface InventoryStatisticsMapper extends CommonMapper<TbWhInventory> {

    List<LineBarDto> queryLineBarDataByInOutType(@Param("inOutType") Integer inOutType);

    List<PieChartDto> queryPieChartData();

    Integer queryStatisticsOrderCount();
}
