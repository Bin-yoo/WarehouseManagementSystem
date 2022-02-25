package me.zhengjie.modules.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.PurchaseStatisticsService;
import me.zhengjie.modules.statistics.service.dto.PurchaseStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferDetailDto;
import me.zhengjie.modules.statistics.service.dto.PurchaseStatisticsQueryParam;
import me.zhengjie.modules.statistics.service.mapper.PurchaseStatisticsMapper;
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
public class PurchaseStatisticsServiceImpl extends CommonServiceImpl<PurchaseStatisticsMapper, TbWhInventory> implements PurchaseStatisticsService {

    // private final RedisUtils redisUtils;
    private final PurchaseStatisticsMapper purchaseStatisticsMapper;

    @Override
    public PageInfo<PurchaseStatisticsDto> queryAll(PurchaseStatisticsQueryParam query, Pageable pageable) {
        IPage<PurchaseStatisticsDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<PurchaseStatisticsDto> page = purchaseStatisticsMapper.queryAll(queryPage, query);
        return ConvertUtil.convertPage(page, PurchaseStatisticsDto.class);
    }

    @Override
    public Object getDetail(Long goodId, Pageable pageable) {
        IPage<SupplierOfferDetailDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<SupplierOfferDetailDto> page = purchaseStatisticsMapper.getDetail(goodId, queryPage);
        return ConvertUtil.convertPage(page, SupplierOfferDetailDto.class);
    }
}
