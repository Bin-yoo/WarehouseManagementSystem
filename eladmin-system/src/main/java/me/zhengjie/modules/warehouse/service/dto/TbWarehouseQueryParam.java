package me.zhengjie.modules.warehouse.service.dto;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.annotation.Query;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Getter
@Setter
public class TbWarehouseQueryParam{

    @Query(type = Query.Type.INNER_LIKE)
    private String whName;

    /** 精确 */
    @Query
    private String deptId;

    @Query(type = Query.Type.INNER_LIKE)
    private String keeper;

    @Query(type = Query.Type.INNER_LIKE)
    private String director;

}
