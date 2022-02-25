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
public class SellStatisticsDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long goodId;

    @ApiModelProperty(value = "货品编码")
    private String gCode;

    @ApiModelProperty(value = "货品名称")
    private String gName;

    @ApiModelProperty(value = "规格")
    private String specification;

    @ApiModelProperty(value = "拼音码")
    private String pyCode;

    @ApiModelProperty(value = "货品类别名称")
    private String typeName;

    @ApiModelProperty(value = "计量单位名称")
    private String unitName;

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
