package me.zhengjie.modules.orders.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
* @author LiangBin
* @date 2022-01-03
*/
@Data
public class OrderVo implements Serializable {

    @ApiModelProperty(value = "单据id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "制单日期")
    private Date orderDate;

    @ApiModelProperty(value = "制单人id")
    private Long orderPersonId;

    @ApiModelProperty(value = "制单人名称")
    private String orderPerson;

    @ApiModelProperty(value = "经办人id")
    private Long managerId;

    @ApiModelProperty(value = "经办人名称")
    private String manager;

    @ApiModelProperty(value = "入库日期")
    private Date date;

    @ApiModelProperty(value = "存入仓库id")
    private Long whId;

    @ApiModelProperty(value = "存入仓库名称")
    private String whName;

    @ApiModelProperty(value = "货品来源id(来往单位或部门id)")
    private Long sourceId;

    @ApiModelProperty(value = "货品来源(来往单位、部门或仓库名称)")
    private String sourceName;

    @ApiModelProperty(value = "原始单号")
    private String originOrderNo;

    @ApiModelProperty(value = "大写金额")
    private String upperCasePrice;

    @ApiModelProperty(value = "合计数量")
    private String amountCount;

    @ApiModelProperty(value = "合计金额")
    private String amountPrice;

    @ApiModelProperty(value = "备注")
    @Length(max = 100, message = "备注长度不能超过100个字符")
    private String remark;

    @ApiModelProperty(value = "货品列表")
    private List<GoodsInfoVo> goodList;
}
