package me.zhengjie.modules.tb_goods_type.service.dto;

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
* @date 2021-12-01
*/
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TbGoodsTypeDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "货品类型id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "货品类型名称")
    private String gtName;

    @ApiModelProperty(value = "父级id")
    private Long parentId;

    @ApiModelProperty(value = "是否文件夹")
    private String isFolder;
}
