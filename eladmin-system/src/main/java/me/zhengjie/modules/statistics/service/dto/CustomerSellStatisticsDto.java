package me.zhengjie.modules.statistics.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author jinjin
* @date 2020-10-01
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CustomerSellStatisticsDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long sourceId;

    @ApiModelProperty(value = "客户")
    private String sourceName;

    @ApiModelProperty(value = "销售数量")
    private Integer sellCount = 0;

    @ApiModelProperty(value = "销售金额")
    private BigDecimal sellAmount = new BigDecimal("0");

    @ApiModelProperty(value = "退货数量")
    private Integer refundCount = 0;

    @ApiModelProperty(value = "退货金额")
    private BigDecimal refundAmount = new BigDecimal("0");

    @ApiModelProperty(value = "总数量")
    private Integer totalCount = 0;

    @ApiModelProperty(value = "总金额")
    private BigDecimal totalAmount = new BigDecimal("0");
}
