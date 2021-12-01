package me.zhengjie.modules.tb_purchase_order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import io.swagger.annotations.ApiModelProperty;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;

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
public class TbPurchaseOrderDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "采购单id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "单号")
    private String orderNo;

    @ApiModelProperty(value = "制单日期")
    private Date orderDate;

    @ApiModelProperty(value = "制单人id")
    private Long orderPersonId;

    @ApiModelProperty(value = "制单人名称")
    private Long orderPerson;

    @ApiModelProperty(value = "入库日期")
    private Date inDate;

    @ApiModelProperty(value = "存入仓库id")
    private Long inWhId;

    @ApiModelProperty(value = "存入仓库名称")
    private String inWhName;

    @ApiModelProperty(value = "供应商id")
    private Long supplierId;

    @ApiModelProperty(value = "供应商名称")
    private String supplier;

    @ApiModelProperty(value = "原始单号")
    private String originOrderNo;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "采购单状态 1 未审批 2 已通过 3 未通过")
    private Integer status;
}
