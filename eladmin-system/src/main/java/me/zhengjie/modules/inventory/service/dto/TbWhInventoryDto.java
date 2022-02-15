package me.zhengjie.modules.inventory.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.math.BigDecimal;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class TbWhInventoryDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "货品id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long goodId;

    @ApiModelProperty(value = "货品编码")
    @NotBlank
    private String gCode;

    @ApiModelProperty(value = "货品名称")
    @NotBlank
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
    @NotNull
    private Long typeId;

    @ApiModelProperty(value = "货品类别名称")
    private String type;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "重量")
    private String weight;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "计量单位")
    @NotNull
    private Long unitId;

    @ApiModelProperty(value = "计量单位名称")
    private String unit;

    @ApiModelProperty(value = "货位")
    private Integer allocation;

    @ApiModelProperty(value = "仓库id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long whId;

    @ApiModelProperty(value = "仓库名称")
    @NotBlank
    private String whName;

    @ApiModelProperty(value = "货品数量")
    private Integer count;

    @ApiModelProperty(value = "库存下限")
    private Integer lowerLimit;

    @ApiModelProperty(value = "库存上限")
    private Integer upperLimit;
}
