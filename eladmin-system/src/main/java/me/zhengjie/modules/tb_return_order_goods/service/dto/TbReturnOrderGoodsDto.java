package me.zhengjie.modules.tb_return_order_goods.service.dto;

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
public class TbReturnOrderGoodsDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "采购单d")
    private String orderId;

    @ApiModelProperty(value = "货品id")
    private String goodId;

    @ApiModelProperty(value = "货品数量")
    private String goodNum;

    @ApiModelProperty(value = "单价")
    private Integer price;

    @ApiModelProperty(value = "总价金额")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "备注")
    private String remark;
}
