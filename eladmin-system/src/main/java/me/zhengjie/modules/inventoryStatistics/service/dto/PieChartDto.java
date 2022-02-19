package me.zhengjie.modules.inventoryStatistics.service.dto;

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
public class PieChartDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private BigDecimal value;

    private BigDecimal percentage;

}
