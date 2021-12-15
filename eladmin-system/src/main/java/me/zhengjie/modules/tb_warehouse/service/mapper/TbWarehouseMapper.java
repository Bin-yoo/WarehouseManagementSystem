package me.zhengjie.modules.tb_warehouse.service.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.github.yulichang.base.MPJBaseMapper;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.tb_warehouse.domain.TbWarehouse;
import me.zhengjie.modules.tb_warehouse.service.dto.TbWarehouseDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Repository
public interface TbWarehouseMapper extends MPJBaseMapper<TbWarehouse> {
    IPage<TbWarehouseDto> queryAll(IPage<TbWarehouseDto> queryPage, @Param(Constants.WRAPPER) Wrapper<TbWarehouseDto> wrapper);
}
