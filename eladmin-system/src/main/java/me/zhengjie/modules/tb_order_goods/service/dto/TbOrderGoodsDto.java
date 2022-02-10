package me.zhengjie.modules.tb_order_goods.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import io.swagger.annotations.ApiModelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

/**
* @author LiangBin
* @date 2022-02-10
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TbOrderGoodsDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "采购单d")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long orderId;

    @ApiModelProperty(value = "货品id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long goodId;

    @ApiModelProperty(value = "货品数量")
    private String goodNum;

    @ApiModelProperty(value = "单价")
    private BigDecimal price;

    @ApiModelProperty(value = "总价金额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "批次")
    private String batch;

    @ApiModelProperty(value = "生产日期")
    private Date productDate;

    @ApiModelProperty(value = "有效日期")
    private Date validDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "账面数量")
    private String paperGoodNum;

    @ApiModelProperty(value = "实盘数量")
    private String inventoryGoodNum;

    @ApiModelProperty(value = "盈亏数量")
    private String plGoodNum;

    @ApiModelProperty(value = "盈亏金额")
    private BigDecimal plPrice;
}
