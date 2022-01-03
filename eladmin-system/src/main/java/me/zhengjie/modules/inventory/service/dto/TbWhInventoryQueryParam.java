package me.zhengjie.modules.inventory.service.dto;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.annotation.Query;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Getter
@Setter
public class TbWhInventoryQueryParam{

    @Query(type = Query.Type.INNER_LIKE)
    private String gName;

    @Query(type = Query.Type.INNER_LIKE)
    private String gCode;

    @Query(type = Query.Type.INNER_LIKE)
    private String pyCode;

    /** 精确 */
    @Query
    private Long whId;

    /** 精确 */
    @Query
    private Long typeId;

    /** 精确 */
    @Query
    private Long unitId;

}
