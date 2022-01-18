package me.zhengjie.modules.orders.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
* @author LiangBin
* @date 2021-12-21
*/
@Data
public class ReportGoodsListVo implements Serializable {

    @ApiModelProperty(value = "序号")
    private String index;

    @ApiModelProperty(value = "货品编码")
    private String gCode;

    @ApiModelProperty(value = "货品名称")
    private String gName;

    @ApiModelProperty(value = "规格")
    private String specification;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "计量单位名称")
    private String unitName;

    @ApiModelProperty(value = "进价")
    private BigDecimal purchasePrice;

    @ApiModelProperty(value = "售价")
    private BigDecimal sellPrice;

    @ApiModelProperty(value = "数量")
    private Integer goodNum = 1;

    @ApiModelProperty(value = "总金额")
    private BigDecimal totalPrice = new BigDecimal(0);

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getgCode() {
        return gCode;
    }

    public void setgCode(String gCode) {
        this.gCode = gCode;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Integer getGoodNum() {
        return goodNum;
    }

    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
