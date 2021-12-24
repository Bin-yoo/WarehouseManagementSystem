package me.zhengjie.modules.goodsunit.service.dto;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.annotation.Query;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Getter
@Setter
public class TbGoodsUnitQueryParam{
    @Query(blurry = "guName")
    private String blurry;
}
