package me.zhengjie.modules.orders.service.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Date;
import me.zhengjie.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

/**
* @author LiangBin
* @date 2022-01-03
*/
@Getter
@Setter
public class TbOrdersQueryParam{

    /** 精确 */
    @Query
    private Integer orderType;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String orderNo;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String orderPerson;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String manager;

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

    /** 精确 */
    @Query
    private Long sourceId;

}
