package me.zhengjie.modules.inventory.service.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryDto;
import me.zhengjie.modules.warehouse.service.dto.TbWarehouseDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Repository
public interface TbWhInventoryMapper extends CommonMapper<TbWhInventory> {
    IPage<TbWhInventoryDto> queryAllWareHouse(IPage<TbWhInventoryDto> queryPage, @Param(Constants.WRAPPER) Wrapper<TbWhInventoryDto> wrapper);

    IPage<TbWhInventoryDto> queryByWareHouse(IPage<TbWhInventoryDto> queryPage, @Param(Constants.WRAPPER) Wrapper<TbWhInventoryDto> wrapper);

}
