package me.zhengjie.modules.tb_warehouse.service.dto;

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
public class TbWarehouseDto implements Serializable {
    private static final long serialVersionUID = 1L;

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
