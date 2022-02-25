package me.zhengjie.modules.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.CustomerSellStatisticsService;
import me.zhengjie.modules.statistics.service.dto.CustomerSellStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.PurchaseStatisticsQueryParam;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferDetailDto;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferStatisticsDto;
import me.zhengjie.modules.statistics.service.mapper.CustomerSellStatisticsMapper;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbWhInventoryService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CustomerSellStatisticsServiceImpl extends CommonServiceImpl<CustomerSellStatisticsMapper, TbWhInventory> implements CustomerSellStatisticsService {

    // private final RedisUtils redisUtils;
    private final CustomerSellStatisticsMapper customerSellStatisticsMapper;

    @Override
    public PageInfo<CustomerSellStatisticsDto> queryAll(PurchaseStatisticsQueryParam query, Pageable pageable) {
        IPage<CustomerSellStatisticsDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<CustomerSellStatisticsDto> page = customerSellStatisticsMapper.queryAll(queryPage, query);
        return ConvertUtil.convertPage(page, CustomerSellStatisticsDto.class);
    }

    @Override
    public PageInfo<SupplierOfferDetailDto> getDetail(Long sourceId, Pageable pageable) {
        IPage<SupplierOfferDetailDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<SupplierOfferDetailDto> page = customerSellStatisticsMapper.getDetail(queryPage, sourceId);
        return ConvertUtil.convertPage(page, SupplierOfferDetailDto.class);
    }
}
