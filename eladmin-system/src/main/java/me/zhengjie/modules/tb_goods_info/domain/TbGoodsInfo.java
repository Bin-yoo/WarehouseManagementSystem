package me.zhengjie.modules.tb_goods_info.domain;

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
import java.math.BigDecimal;
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

    @ApiModelProperty(value = "单位")
    @NotNull
    private Long gUnit;

    @ApiModelProperty(value = "货品类别")
    @NotNull
    private Long gType;

    @ApiModelProperty(value = "进价")
    private BigDecimal pPrice;

    @ApiModelProperty(value = "售价")
    private BigDecimal sPrice;

    @ApiModelProperty(value = "库存上限")
    private Integer uLimit;

    @ApiModelProperty(value = "库存下限")
    private Integer lLimit;

    @ApiModelProperty(value = "货位")
    private Integer gAllocation;

    @ApiModelProperty(value = "生产厂家")
    private String manufacturer;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "批次")
    private String batch;

    @ApiModelProperty(value = "生产日期")
    private Date productDate;

    @ApiModelProperty(value = "有效日期")
    private Date validDate;

    @ApiModelProperty(value = "物资来源")
    private String source;

    @ApiModelProperty(value = "条形码")
    private String barCode;

    @ApiModelProperty(value = "二维码")
    private String qrCode;

    public void copyFrom(TbGoodsInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
