package me.zhengjie.modules.tb_wh_goods.service.dto;

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
* @date 2021-12-21
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TbWhGoodsDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "货品id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long goodId;

    //@ApiModelProperty(value = "货品名称")
    //private String goodName;

    @ApiModelProperty(value = "仓库id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long whId;

    @ApiModelProperty(value = "仓库名称")
    private String whName;

    @ApiModelProperty(value = "仓初数量")
    private Integer initialCount;

    @ApiModelProperty(value = "库存下限")
    private Integer lowerLimit;

    @ApiModelProperty(value = "库存上限")
    private Integer upperLimit;
}
