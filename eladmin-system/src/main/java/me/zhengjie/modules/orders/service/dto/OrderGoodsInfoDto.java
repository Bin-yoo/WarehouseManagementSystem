package me.zhengjie.modules.orders.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author LiangBin
* @date 2021-12-21
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OrderGoodsInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "货品id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "货品编码")
    private String gCode;

    @ApiModelProperty(value = "货品名称")
    private String gName;

    @ApiModelProperty(value = "规格")
    private String specification;

    @ApiModelProperty(value = "拼音码")
    private String pyCode;

    @ApiModelProperty(value = "生产厂家")
    private String manufacturer;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "条形码")
    private String barCode;

    @ApiModelProperty(value = "二维码")
    private String qrCode;

    @ApiModelProperty(value = "货品类别")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long type;

    @ApiModelProperty(value = "货品类别名称")
    private String typeName;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "重量")
    private String weight;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "计量单位")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long unit;

    @ApiModelProperty(value = "计量单位名称")
    private String unitName;

    @ApiModelProperty(value = "进价")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "售价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "货位")
    private Integer allocation;

    @ApiModelProperty(value = "货品图片")
    private String pic;

    @ApiModelProperty(value = "货品库存")
    private Integer count;

    @ApiModelProperty(value = "数量")
    private Integer goodNum;

    @ApiModelProperty(value = "总金额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "账面数量")
    private Integer paperGoodNum;

    @ApiModelProperty(value = "实盘数量")
    private Integer inventoryGoodNum;

    @ApiModelProperty(value = "盈亏数量")
    private Integer plGoodNum;

    @ApiModelProperty(value = "盈亏金额")
    private BigDecimal plPrice;
}
