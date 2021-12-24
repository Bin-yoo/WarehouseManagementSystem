package me.zhengjie.modules.partnercompanyinfo.service;

import me.zhengjie.base.PageInfo;
import me.zhengjie.base.CommonService;
import me.zhengjie.modules.partnercompanyinfo.domain.TbPartnerCompanyInfo;
import me.zhengjie.modules.partnercompanyinfo.service.dto.TbPartnerCompanyInfoDto;
import me.zhengjie.modules.partnercompanyinfo.service.dto.TbPartnerCompanyInfoQueryParam;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
public interface TbPartnerCompanyInfoService extends CommonService<TbPartnerCompanyInfo>  {

    static final String CACHE_KEY = "tbPartnerCompanyInfo";

    /**
    * 查询数据分页
    * @param query 条件
    * @param pageable 分页参数
    * @return PageInfo<TbPartnerCompanyInfoDto>
    */
    PageInfo<TbPartnerCompanyInfoDto> queryAll(TbPartnerCompanyInfoQueryParam query, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param query 条件参数
    * @return List<TbPartnerCompanyInfoDto>
    */
    List<TbPartnerCompanyInfoDto> queryAll(TbPartnerCompanyInfoQueryParam query);

    TbPartnerCompanyInfo getById(Long id);
    TbPartnerCompanyInfoDto findById(Long id);

    /**
     * 插入一条新数据。
     */
    int insert(TbPartnerCompanyInfoDto resources);
    int updateById(TbPartnerCompanyInfoDto resources);
    int removeById(Long id);
    int removeByIds(Set<Long> ids);

    Object getCompanyCode();

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    // void download(List<TbPartnerCompanyInfoDto> all, HttpServletResponse response) throws IOException;
}
