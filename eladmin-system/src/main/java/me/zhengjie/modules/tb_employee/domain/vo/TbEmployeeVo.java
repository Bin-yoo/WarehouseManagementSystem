package me.zhengjie.modules.tb_employee.domain.vo;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
* @author LiangBin
* @date 2021-12-08
*/
@Data
public class TbEmployeeVo implements Serializable {

    @ApiModelProperty(value = "员工id")
    /** 防止精度丢失 */
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "拼音码")
    private String pyCode;

    @ApiModelProperty(value = "所属部门")
    private Long deptId;

    @ApiModelProperty(value = "所属部门名称")
    private String deptName;

    @ApiModelProperty(value = "籍贯")
    private String jg;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "手机号码")
    private String mobile;

    @ApiModelProperty(value = "家庭住址")
    private String address;

    @ApiModelProperty(value = "职位")
    private String position;

    @ApiModelProperty(value = "职称")
    private String title;

    @ApiModelProperty(value = "生日")
    @DateTimeFormat("yyyy-MM-dd")
    private Date birthday;
}
