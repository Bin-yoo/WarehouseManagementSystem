package me.zhengjie.modules.inventorywarning.service.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.dto.InventoryDetailDto;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryDto;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryQueryParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Repository
public interface InventoryWarningMapper extends CommonMapper<TbWhInventory> {

    IPage<TbWhInventoryDto> queryAll(IPage<TbWhInventoryDto> queryPage, @Param("query") TbWhInventoryQueryParam query);

}
