package me.zhengjie.modules.inventoryStatistics.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

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
public class LineBarDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日期")
    private String days;

    @ApiModelProperty(value = "数据")
    private Integer count;

}
