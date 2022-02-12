package me.zhengjie.modules.inventory.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class InventoryDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单号")
    private String orderNo;

    @ApiModelProperty(value = "单据类型")
    private Integer orderType;

    @ApiModelProperty(value = "货品编码")
    private String gCode;

    @ApiModelProperty(value = "货品名称")
    private String gName;

    @ApiModelProperty(value = "拼音码")
    private String pyCode;

    @ApiModelProperty(value = "规格")
    private String specification;

    @ApiModelProperty(value = "单位")
    private String unitName;

    @ApiModelProperty(value = "单价")
    private String price;

    @ApiModelProperty(value = "数量")
    private String count;

    @ApiModelProperty(value = "金额")
    private String totalPrice;

    @ApiModelProperty(value = "仓库")
    private String whName;

    @ApiModelProperty(value = "来往单位/部门")
    private String source;

}
