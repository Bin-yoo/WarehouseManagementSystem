package me.zhengjie.modules.tb_wh_inventory.service.dto;

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
import java.util.Date;
import java.sql.Timestamp;
import io.swagger.annotations.ApiModelProperty;

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

    @ApiModelProperty(value = "仓库id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long whId;

    @ApiModelProperty(value = "货品数量")
    private Integer count;
}
