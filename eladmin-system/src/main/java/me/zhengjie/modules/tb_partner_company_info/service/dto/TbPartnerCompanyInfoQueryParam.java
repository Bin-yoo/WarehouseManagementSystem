package me.zhengjie.modules.tb_partner_company_info.service.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Date;
import me.zhengjie.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

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
