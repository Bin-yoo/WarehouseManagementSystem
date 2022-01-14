package me.zhengjie.modules.goodsinfo.service.dto;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.annotation.Query;

/**
* @author LiangBin
* @date 2021-12-21
*/
@Getter
@Setter
public class TbGoodsInfoQueryParam{

    @Query(type = Query.Type.INNER_LIKE)
    private String gName;

    @Query(type = Query.Type.INNER_LIKE)
    private String gCode;

    @Query(type = Query.Type.INNER_LIKE)
    private String pyCode;

    /** 精确 */
    @Query
    private Long type;

    /** 精确 */
    @Query
    private Long unit;

    /** 精确 */
    @Query
    private Long whId;

}
