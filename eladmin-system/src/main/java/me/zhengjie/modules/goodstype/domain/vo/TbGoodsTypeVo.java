package me.zhengjie.modules.goodstype.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author LiangBin
 * @Description
 * @date 2021/12/2 15:42
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TbGoodsTypeVo implements Serializable {

    @ApiModelProperty(value = "货品类型id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "货品类型名称")
    private String gtName;

    @ApiModelProperty(value = "父级id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long parentId;

    @ApiModelProperty(value = "是否文件夹")
    private String isFolder;

    private List<TbGoodsTypeVo> children;

}
