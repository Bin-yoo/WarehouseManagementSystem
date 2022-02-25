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
public class DeptProductStatisticsDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long sourceId;

    @ApiModelProperty(value = "供应商")
    private String sourceName;

    @ApiModelProperty(value = "采购数量")
    private Integer productCount = 0;

    @ApiModelProperty(value = "采购金额")
    private BigDecimal productAmount = new BigDecimal("0");

}
