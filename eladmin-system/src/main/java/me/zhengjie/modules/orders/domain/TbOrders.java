package me.zhengjie.modules.orders.domain;

import com.baomidou.mybatisplus.annotation.*;
import me.zhengjie.base.CommonModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;

/**
* @author LiangBin
* @date 2022-01-03
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_orders")
public class TbOrders extends CommonModel<TbOrders> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单据id")
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "单据类型")
    private Integer orderType;

    @ApiModelProperty(value = "单号")
    @NotBlank
    private String orderNo;

    @ApiModelProperty(value = "制单日期")
    @NotNull
    private Date orderDate;

    @ApiModelProperty(value = "制单人id")
    @NotNull
    private Long orderPersonId;

    @ApiModelProperty(value = "制单人名称")
    @NotBlank
    private String orderPerson;

    @ApiModelProperty(value = "经办人id")
    @NotNull
    private Long managerId;

    @ApiModelProperty(value = "经办人名称")
    private String manager;

    @ApiModelProperty(value = "入库日期")
    @NotNull
    private Date date;

    @ApiModelProperty(value = "存入仓库id")
    @NotNull
    private Long whId;

    @ApiModelProperty(value = "存入仓库名称")
    private String whName;

    @ApiModelProperty(value = "货品来源id(来往单位或部门id)")
    private Long sourceId;

    @ApiModelProperty(value = "货品来源(来往单位、部门或仓库名称)")
    private String sourceName;

    @ApiModelProperty(value = "原始单号")
    @NotBlank
    private String originOrderNo;

    @ApiModelProperty(value = "大写金额")
    private String upperCasePrice;

    @ApiModelProperty(value = "合计数量")
    private String amountCount;

    @ApiModelProperty(value = "合计金额")
    private String amountPrice;

    @ApiModelProperty(value = "采购单状态 1 未审批 2 已通过 3 反审核")
    private Integer status;

    @ApiModelProperty(value = "审核日期")
    private Date verifyDate;

    @ApiModelProperty(value = "审核人id")
    private Long verifyPersonId;

    @ApiModelProperty(value = "审核人名称")
    private String verifyPerson;

    @ApiModelProperty(value = "删除标识")
    @TableLogic
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private Boolean delFlag;

    @ApiModelProperty(value = "最后修改日期")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty(value = "最后修改人")
    @TableField(fill= FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty(value = "备注")
    private String remark;

    public void copyFrom(TbOrders source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
