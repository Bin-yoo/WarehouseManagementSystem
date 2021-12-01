package me.zhengjie.modules.tb_product_order.domain;

import me.zhengjie.base.CommonModel;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.IdType;
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
import java.sql.Timestamp;

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
@TableName("tb_product_order")
public class TbProductOrder extends CommonModel<TbProductOrder> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "采购单id")
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

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
    @NotNull
    private Long orderPerson;

    @ApiModelProperty(value = "入库日期")
    @NotNull
    private Date inDate;

    @ApiModelProperty(value = "存入仓库id")
    @NotNull
    private Long inWhId;

    @ApiModelProperty(value = "存入仓库名称")
    private String inWhName;

    @ApiModelProperty(value = "生产部门id")
    @NotNull
    private Long deptId;

    @ApiModelProperty(value = "生产部门名称")
    private String deptName;

    @ApiModelProperty(value = "原始单号")
    private String originOrderNo;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "采购单状态 1 未审批 2 已通过 3 未通过")
    private Integer status;

    public void copyFrom(TbProductOrder source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
