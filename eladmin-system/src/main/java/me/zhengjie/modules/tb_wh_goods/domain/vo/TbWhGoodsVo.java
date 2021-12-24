package me.zhengjie.modules.tb_wh_goods.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author LiangBin
 * @Description
 * @date 2021/12/21 15:40
 */
@Data
public class TbWhGoodsVo {

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

    public TbWhGoodsVo() {
        this.initialCount = 0;
        this.lowerLimit = 0;
        this.upperLimit = 0;
    }
}
