package me.zhengjie.modules.tb_order_goods.domain;

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
* @date 2021-12-20
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_order_goods")
public class TbOrderGoods extends CommonModel<TbOrderGoods> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "采购单id")
    //@TableId(type= IdType.ASSIGN_ID)
    @NotNull
    private Long orderId;

    @ApiModelProperty(value = "货品id")
    //@TableId(type= IdType.ASSIGN_ID)
    @NotNull
    private Long goodId;

    @ApiModelProperty(value = "货品数量")
    @NotBlank
    private String goodNum;

    @ApiModelProperty(value = "单价")
    @NotNull
    private BigDecimal price;

    @ApiModelProperty(value = "总价金额")
    @NotNull
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "批次")
    private String batch;

    @ApiModelProperty(value = "生产日期")
    private Date productDate;

    @ApiModelProperty(value = "有效日期")
    private Date validDate;

    @ApiModelProperty(value = "备注")
    private String remark;

    public void copyFrom(TbOrderGoods source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
