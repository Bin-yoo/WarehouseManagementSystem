package me.zhengjie.modules.orders.service.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.goodsinfo.service.dto.TbGoodsInfoDto;
import me.zhengjie.modules.orders.domain.TbOrders;
import me.zhengjie.modules.orders.service.dto.OrderGoodsInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author LiangBin
* @date 2022-01-03
*/
@Repository
public interface TbOrdersMapper extends CommonMapper<TbOrders> {

    IPage<TbGoodsInfoDto> getGoodChooseList(IPage<TbGoodsInfoDto> queryPage, @Param(Constants.WRAPPER) QueryWrapper<Object> wrapper);

    List<OrderGoodsInfoDto> getOrderGoodList(@Param("id") String id);
}
