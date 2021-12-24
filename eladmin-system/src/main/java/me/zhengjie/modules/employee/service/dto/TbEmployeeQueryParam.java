package me.zhengjie.modules.employee.service.dto;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.annotation.Query;

/**
* @author LiangBin
* @date 2021-12-08
*/
@Getter
@Setter
public class TbEmployeeQueryParam{

    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    /** 精确 */
    @Query
    private Long deptId;

}
