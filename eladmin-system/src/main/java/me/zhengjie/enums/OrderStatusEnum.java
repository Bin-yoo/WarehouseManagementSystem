package me.zhengjie.enums;

/**
 * @author LiangBin
 * @Description 订单状态枚举类
 * @date 2022/1/3 20:08
 */
public enum OrderStatusEnum {

    NOT_APPROVE(1, "未审批"),
    APPROVE(2, "已审批"),
    RE_APPROVE(3, "反审批");

    private Integer code;

    private String description;

    OrderStatusEnum(Integer code, String description) {
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
        OrderStatusEnum[] orderStatusEnums = values();
        for (OrderStatusEnum orderStatusEnum : orderStatusEnums) {
            if (orderStatusEnum.getCode().equals(code)) {
                return orderStatusEnum.getDescription();
            }
        }
        return null;
    }

    //根据值得到编码
    public static Integer getCode(String val) {
        OrderStatusEnum[] orderStatusEnums = values();
        for (OrderStatusEnum orderStatusEnum : orderStatusEnums) {
            if (orderStatusEnum.getDescription().equals(val)) {
                return orderStatusEnum.getCode();
            }
        }
        return null;
    }
}
