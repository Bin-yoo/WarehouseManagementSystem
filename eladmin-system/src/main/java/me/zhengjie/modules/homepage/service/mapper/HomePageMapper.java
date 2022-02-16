package me.zhengjie.modules.homepage.service.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.homepage.service.dto.LineBarDto;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryDto;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Repository
public interface HomePageMapper extends CommonMapper<TbWhInventory> {

    Integer selectPurchaseOrderCountByMonth(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    Integer selectSellOrderCountByMonth(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    Integer selectWaitApproveOrderCountByMonth();

    Integer selectInventoryWarningCountByMonth();

    List<LineBarDto> queryLineBarDataByOrderType(@Param("orderType") Integer orderType);
}
