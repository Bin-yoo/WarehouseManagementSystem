package me.zhengjie.modules.tb_warehouse.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author LiangBin
 * @Description
 * @date 2021/12/14 17:32
 */
@Data
public class TbWarehouseVo implements Serializable {

    @ApiModelProperty(value = "仓库id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long whId;

    @ApiModelProperty(value = "仓库名称")
    private String whName;

    @ApiModelProperty(value = "所属部门id")
    private Long deptId;

    @ApiModelProperty(value = "所属部门名称")
    private String deptName;

    @ApiModelProperty(value = "库管")
    private String keeper;

    @ApiModelProperty(value = "负责人")
    private String director;

    @ApiModelProperty(value = "库管id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private String keeperId;

    @ApiModelProperty(value = "负责人id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private String directorId;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "存货分类")
    private String goodsType;

    @ApiModelProperty(value = "备注")
    private String remark;

}
