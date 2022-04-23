package me.zhengjie.modules.goodsinfo.service.mapper;

import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.goodsinfo.domain.TbGoodsInfo;
import me.zhengjie.modules.tb_order_goods.domain.TbOrderGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author LiangBin
* @date 2021-12-21
*/
@Repository
public interface TbGoodsInfoMapper extends CommonMapper<TbGoodsInfo> {

    TbOrderGoods getInitialCount(@Param("whId") Long whId, @Param("goodId") Long goodId);
}
