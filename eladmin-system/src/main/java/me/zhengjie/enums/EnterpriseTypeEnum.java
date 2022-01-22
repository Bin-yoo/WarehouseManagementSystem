package me.zhengjie.enums;

/**
 * @author LiangBin
 * @Description 公司类型枚举类
 * @date 2022/1/3 20:08
 */
public enum EnterpriseTypeEnum {

    SUPPLIER(1, "供应商"),
    CUSTOMER(2, "客户"),
    BOTH(3, "供应商、客户");

    private Integer code;

    private String description;

    EnterpriseTypeEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    //根据编码得到值
    public static String getValue(Integer code) {
        EnterpriseTypeEnum[] enterpriseTypeEnums = values();
        for (EnterpriseTypeEnum enterpriseTypeEnum : enterpriseTypeEnums) {
            if (enterpriseTypeEnum.getCode().equals(code)) {
                return enterpriseTypeEnum.getDescription();
            }
        }
        return null;
    }

    //根据值得到编码
    public static Integer getCode(String val) {
        EnterpriseTypeEnum[] enterpriseTypeEnums = values();
        for (EnterpriseTypeEnum enterpriseTypeEnum : enterpriseTypeEnums) {
            if (enterpriseTypeEnum.getDescription().equals(val)) {
                return enterpriseTypeEnum.getCode();
            }
        }
        return null;
    }
}
