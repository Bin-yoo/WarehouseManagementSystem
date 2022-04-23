package me.zhengjie.modules.tb_order_goods.service.mapper;

import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.tb_order_goods.domain.TbOrderGoods;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
* @author LiangBin
* @date 2022-02-10
*/
@Repository
public interface TbOrderGoodsMapper extends CommonMapper<TbOrderGoods> {

    void deleteByOrderId(@Param("orderId") Long orderId);
}
