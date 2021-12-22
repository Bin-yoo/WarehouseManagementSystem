package me.zhengjie.modules.tb_wh_inventory.domain;

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
@TableName("tb_wh_inventory")
public class TbWhInventory extends CommonModel<TbWhInventory> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "货品id")
    //@TableId(type= IdType.ASSIGN_ID)
    @TableField
    @NotNull
    private Long goodId;

    @ApiModelProperty(value = "仓库id")
    //@TableId(type= IdType.ASSIGN_ID)
    @NotNull
    @TableField
    private Long whId;

    @ApiModelProperty(value = "货品数量")
    @NotNull
    private Integer count;

    public void copyFrom(TbWhInventory source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
