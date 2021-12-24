package me.zhengjie.modules.partnercompanyinfo.service.dto;

import lombok.Getter;
import lombok.Setter;
import me.zhengjie.annotation.Query;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Getter
@Setter
public class TbPartnerCompanyInfoQueryParam{
    @Query(blurry = "cCode,cName")
    private String blurry;

    @Query(type = Query.Type.INNER_LIKE)
    private String contactPerson;

    /** 精确 */
    @Query
    private Long type;
}
