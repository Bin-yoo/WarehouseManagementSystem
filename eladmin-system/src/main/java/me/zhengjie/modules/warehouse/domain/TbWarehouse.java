package me.zhengjie.modules.warehouse.domain;

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
@TableName("tb_warehouse")
public class TbWarehouse extends CommonModel<TbWarehouse> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "仓库id")
    @TableId(type= IdType.ASSIGN_ID)
    private Long whId;

    @ApiModelProperty(value = "仓库名称")
    @NotBlank
    private String whName;

    @ApiModelProperty(value = "所属部门")
    private Long deptId;

    @ApiModelProperty(value = "库管id")
    private Long keeperId;

    @ApiModelProperty(value = "负责人id")
    private Long directorId;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "存货分类")
    private String goodsType;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "删除标识")
    @TableLogic
    @TableField(fill=FieldFill.INSERT_UPDATE)
    private Boolean delFlag;

    public void copyFrom(TbWarehouse source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
