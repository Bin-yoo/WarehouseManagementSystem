package me.zhengjie.modules.statistics.service.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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

    @ApiModelProperty(value = "账面数量")
    private Integer paperGoodNum = 0;

    @ApiModelProperty(value = "实盘数量")
    private Integer inventoryGoodNum = 0;

    @ApiModelProperty(value = "盈亏数量")
    private Integer plGoodNum = 0;

    @ApiModelProperty(value = "盈亏金额")
    private BigDecimal plPrice = new BigDecimal(0);

}
