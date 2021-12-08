package me.zhengjie.modules.tb_employee.service.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Date;
import me.zhengjie.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

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
