package me.zhengjie.modules.searchorders.service.mapper;

import me.zhengjie.base.CommonMapper;
import me.zhengjie.modules.orders.domain.TbOrders;
import me.zhengjie.modules.searchorders.service.dto.SearchOrderGoodsInfoDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
* @author LiangBin
* @date 2022-01-03
*/
@Repository
public interface SearchOrdersMapper extends CommonMapper<TbOrders> {

    List<SearchOrderGoodsInfoDto> getInventoryOrderGoodList(@Param("id") String id);
}
