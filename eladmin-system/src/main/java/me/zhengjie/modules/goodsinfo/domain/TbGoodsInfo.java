package me.zhengjie.modules.goodsinfo.domain;

import me.zhengjie.base.CommonModel;
import com.baomidou.mybatisplus.annotation.TableName;
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
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @author LiangBin
* @date 2021-12-21
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_goods_info")
public class TbGoodsInfo extends CommonModel<TbGoodsInfo> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "货品id")
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

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
    private Long type;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "重量")
    private String weight;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "计量单位")
    @NotNull
    private Long unit;

    @ApiModelProperty(value = "进价")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "售价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "货位")
    private Integer allocation;

    @ApiModelProperty(value = "货品图片")
    private String pic;

    public void copyFrom(TbGoodsInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
