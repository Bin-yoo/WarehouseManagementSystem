package me.zhengjie.modules.statistics.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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
public class SupplierOfferDetailDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单号")
    private String orderNo;

    @ApiModelProperty(value = "单据类型")
    private Integer orderType;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "入库日期")
    private Date date;

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
    private String sourceName;

}
