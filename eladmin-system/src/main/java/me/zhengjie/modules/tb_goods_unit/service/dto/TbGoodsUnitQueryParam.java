package me.zhengjie.modules.tb_goods_unit.service.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Date;
import me.zhengjie.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

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