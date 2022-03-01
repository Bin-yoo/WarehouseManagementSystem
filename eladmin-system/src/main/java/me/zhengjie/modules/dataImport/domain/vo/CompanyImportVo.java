package me.zhengjie.modules.dataImport.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Data
public class CompanyImportVo implements Serializable {

    @ApiModelProperty(value = "单位名称")
    private String cName;

    @ApiModelProperty(value = "单位编码")
    private String cCode;

    @ApiModelProperty(value = "单位类别")
    private String typeName;

    @ApiModelProperty(value = "拼音码")
    private String pyCode;

    @ApiModelProperty(value = "所在省份")
    private String province;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "联系人")
    private String contactPerson;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "手机")
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
}
