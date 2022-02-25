package me.zhengjie.modules.statistics.service.dto;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.annotation.Query;

/**
* @author LiangBin
* @date 2022-01-03
*/
@Getter
@Setter
public class ProductStatisticsQueryParam {

    @Query(type = Query.Type.INNER_LIKE)
    private String gName;

    @Query(type = Query.Type.INNER_LIKE)
    private String gCode;

    @Query(type = Query.Type.INNER_LIKE)
    private String pyCode;

    /** 精确 */
    @Query
    private Long typeId;

    /** 精确 */
    @Query
    private Long unitId;

    /** 精确 */
    @Query
    private Long whId;

    /** 精确 */
    @Query
    private Long sourceId;

    @Query(type = Query.Type.INNER_LIKE)
    private String sourceName;

    @Query(type = Query.Type.INNER_LIKE)
    private String deptName;

}
