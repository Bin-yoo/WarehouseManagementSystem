package me.zhengjie.enums;

/**
 * @author LiangBin
 * @Description 订单类型枚举类
 * @date 2022/1/3 20:08
 */
public enum OrderTypeEnum {

    PURCHASE(1, "采购入库单"),
    PRODUCE(2, "生产入库单"),
    SELL_RETURN(3, "销售退货单"),
    PURCHASE_RETURN(4, "采购退货单"),
    SELL(5, "销售提货单"),
    TRANSFER(6, "库存调拨单"),
    INVENTORY(7, "库存盘点单");

    private Integer code;

    private String description;

    OrderTypeEnum(Integer code, String description) {
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
        OrderTypeEnum[] orderTypeEnums = values();
        for (OrderTypeEnum orderTypeEnum : orderTypeEnums) {
            if (orderTypeEnum.getCode().equals(code)) {
                return orderTypeEnum.getDescription();
            }
        }
        return null;
    }

    //根据值得到编码
    public static Integer getCode(String val) {
        OrderTypeEnum[] orderTypeEnums = values();
        for (OrderTypeEnum orderTypeEnum : orderTypeEnums) {
            if (orderTypeEnum.getDescription().equals(val)) {
                return orderTypeEnum.getCode();
            }
        }
        return null;
    }
}
