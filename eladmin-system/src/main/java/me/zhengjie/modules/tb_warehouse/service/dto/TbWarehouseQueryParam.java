package me.zhengjie.modules.tb_warehouse.service.dto;

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
