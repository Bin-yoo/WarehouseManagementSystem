package me.zhengjie.modules.searchorders.service.dto;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
* @author LiangBin
* @date 2022-01-03
*/
@Getter
@Setter
public class SearchOrdersQueryParam {

    /** 精确 */
    @Query
    private Integer orderType;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String orderNo;

    /** 精确 */
    @Query
    private Long whId;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String originOrderNo;

    /** BETWEEN */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Query(type = Query.Type.BETWEEN)
    private List<Date> orderDate;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String sourceName;

}
