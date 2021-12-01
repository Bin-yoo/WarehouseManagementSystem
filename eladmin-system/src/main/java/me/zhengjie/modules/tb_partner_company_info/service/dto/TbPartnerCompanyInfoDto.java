package me.zhengjie.modules.tb_partner_company_info.service.dto;

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
public class TbPartnerCompanyInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单位id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "货品编码")
    private String cCode;

    @ApiModelProperty(value = "货品名称")
    private String cName;

    @ApiModelProperty(value = "拼音码")
    private String pyCode;

    @ApiModelProperty(value = "单位类别")
    private Integer type;

    @ApiModelProperty(value = "所在省份")
    private String province;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话")
    private Integer contactPhone;

    @ApiModelProperty(value = "手机")
    private Integer mobile;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "邮编")
    private Integer zip;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "公司网址")
    private String website;

    @ApiModelProperty(value = "QQ号码")
    private Integer qq;

    @ApiModelProperty(value = "银行名称")
    private String bank;

    @ApiModelProperty(value = "银行卡号")
    private String bankAccount;

    @ApiModelProperty(value = "备注")
    private String remark;
}
