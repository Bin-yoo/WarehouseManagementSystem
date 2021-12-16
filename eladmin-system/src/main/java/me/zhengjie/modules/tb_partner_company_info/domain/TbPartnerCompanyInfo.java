package me.zhengjie.modules.tb_partner_company_info.domain;

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
@TableName("tb_partner_company_info")
public class TbPartnerCompanyInfo extends CommonModel<TbPartnerCompanyInfo> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "单位id")
    @TableId(type= IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "单位编码")
    @NotBlank
    private String cCode;

    @ApiModelProperty(value = "单位名称")
    @NotBlank
    private String cName;

    @ApiModelProperty(value = "拼音码")
    @NotBlank
    private String pyCode;

    @ApiModelProperty(value = "单位类别")
    @NotNull
    private Integer type;

    @ApiModelProperty(value = "所在省份")
    private String province;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "联系人")
    @NotBlank
    private String contactPerson;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "手机")
    @NotBlank
    private String mobile;

    @ApiModelProperty(value = "传真")
    private String fax;

    @ApiModelProperty(value = "邮编")
    private String zip;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "公司网址")
    private String website;

    @ApiModelProperty(value = "QQ号码")
    private String qq;

    @ApiModelProperty(value = "银行名称")
    private String bank;

    @ApiModelProperty(value = "银行卡号")
    private String bankAccount;

    @ApiModelProperty(value = "备注")
    private String remark;

    public void copyFrom(TbPartnerCompanyInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
