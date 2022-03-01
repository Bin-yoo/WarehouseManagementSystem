package me.zhengjie.modules.dataImport.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author LiangBin
* @date 2021-12-21
*/
@Data
public class GoodsImportVo implements Serializable {

    @ApiModelProperty(value = "货品名称")
    private String gName;

    @ApiModelProperty(value = "货品编码")
    private String gCode;

    @ApiModelProperty(value = "拼音码")
    private String pyCode;

    @ApiModelProperty(value = "规格")
    private String specification;

    @ApiModelProperty(value = "单位")
    private String unitName;

    @ApiModelProperty(value = "货品类别")
    private String typeName;

    @ApiModelProperty(value = "型号")
    private String model;

    @ApiModelProperty(value = "颜色")
    private String color;

    @ApiModelProperty(value = "重量")
    private String weight;

    @ApiModelProperty(value = "货位")
    private String allocation;

    @ApiModelProperty(value = "生产厂家")
    private String manufacturer;

    @ApiModelProperty(value = "进价")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "售价")
    private BigDecimal sellPrice;

    //@ApiModelProperty(value = "条形码")
    //private String barCode;
    //
    //@ApiModelProperty(value = "二维码")
    //private String qrCode;

}
