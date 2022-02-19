package me.zhengjie.modules.inventoryStatistics.service;

import me.zhengjie.base.CommonService;
import me.zhengjie.modules.inventory.domain.TbWhInventory;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface InventoryStatisticsService extends CommonService<TbWhInventory>  {

    static final String CACHE_KEY = "tbWhInventory";

    Object queryLineBarData();

    Object queryPieChartData();
}
