package me.zhengjie.modules.tb_goods_info.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
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
public class TbGoodsInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "货品id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "货品编码")
    private String gCode;

    @ApiModelProperty(value = "货品名称")
    private String gName;

    @ApiModelProperty(value = "规格")
    private String specification;

    @ApiModelProperty(value = "拼音码")
    private String pyCode;

    @ApiModelProperty(value = "单位")
    private Long gUnit;

    @ApiModelProperty(value = "货品类别")
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
}
