package me.zhengjie.modules.tb_partner_company_info.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_partner_company_info.domain.TbPartnerCompanyInfo;
import me.zhengjie.modules.tb_partner_company_info.service.TbPartnerCompanyInfoService;
import me.zhengjie.modules.tb_partner_company_info.service.dto.TbPartnerCompanyInfoDto;
import me.zhengjie.modules.tb_partner_company_info.service.dto.TbPartnerCompanyInfoQueryParam;
import me.zhengjie.modules.tb_partner_company_info.service.mapper.TbPartnerCompanyInfoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import java.util.*;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbPartnerCompanyInfoService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbPartnerCompanyInfoServiceImpl extends CommonServiceImpl<TbPartnerCompanyInfoMapper, TbPartnerCompanyInfo> implements TbPartnerCompanyInfoService {

    // private final RedisUtils redisUtils;
    private final TbPartnerCompanyInfoMapper tbPartnerCompanyInfoMapper;

    @Override
    public PageInfo<TbPartnerCompanyInfoDto> queryAll(TbPartnerCompanyInfoQueryParam query, Pageable pageable) {
        IPage<TbPartnerCompanyInfo> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbPartnerCompanyInfo> page = tbPartnerCompanyInfoMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbPartnerCompanyInfoDto.class);
    }

    @Override
    public List<TbPartnerCompanyInfoDto> queryAll(TbPartnerCompanyInfoQueryParam query){
        return ConvertUtil.convertList(tbPartnerCompanyInfoMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbPartnerCompanyInfoDto.class);
    }

    @Override
    public TbPartnerCompanyInfo getById(Long id) {
        return tbPartnerCompanyInfoMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbPartnerCompanyInfoDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbPartnerCompanyInfoDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbPartnerCompanyInfoDto resources) {
        TbPartnerCompanyInfo entity = ConvertUtil.convert(resources, TbPartnerCompanyInfo.class);
        return tbPartnerCompanyInfoMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbPartnerCompanyInfoDto resources){
        TbPartnerCompanyInfo entity = ConvertUtil.convert(resources, TbPartnerCompanyInfo.class);
        int ret = tbPartnerCompanyInfoMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbPartnerCompanyInfoMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Long id){
        Set<Long> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    /*
    private void delCaches(Long id) {
        redisUtils.delByKey(CACHE_KEY + "::id:", id);
    }

    private void delCaches(Set<Long> ids) {
        for (Long id: ids) {
            delCaches(id);
        }
    }*/

    /*
    @Override
    public void download(List<TbPartnerCompanyInfoDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbPartnerCompanyInfoDto tbPartnerCompanyInfo : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("货品编码", tbPartnerCompanyInfo.getCCode());
              map.put("货品名称", tbPartnerCompanyInfo.getCName());
              map.put("拼音码", tbPartnerCompanyInfo.getPyCode());
              map.put("单位类别", tbPartnerCompanyInfo.getType());
              map.put("所在省份", tbPartnerCompanyInfo.getProvince());
              map.put("详细地址", tbPartnerCompanyInfo.getAddress());
              map.put("联系人", tbPartnerCompanyInfo.getContactPerson());
              map.put("联系电话", tbPartnerCompanyInfo.getContactPhone());
              map.put("手机", tbPartnerCompanyInfo.getMobile());
              map.put("传真", tbPartnerCompanyInfo.getFax());
              map.put("邮编", tbPartnerCompanyInfo.getZip());
              map.put("邮箱", tbPartnerCompanyInfo.getEmail());
              map.put("公司网址", tbPartnerCompanyInfo.getWebsite());
              map.put("QQ号码", tbPartnerCompanyInfo.getQq());
              map.put("银行名称", tbPartnerCompanyInfo.getBank());
              map.put("银行卡号", tbPartnerCompanyInfo.getBankAccount());
              map.put("备注", tbPartnerCompanyInfo.getRemark());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
