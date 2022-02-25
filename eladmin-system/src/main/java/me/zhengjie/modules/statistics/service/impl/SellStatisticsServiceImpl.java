package me.zhengjie.modules.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.SellStatisticsService;
import me.zhengjie.modules.statistics.service.dto.PurchaseStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.SellStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.SellStatisticsQueryParam;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferDetailDto;
import me.zhengjie.modules.statistics.service.mapper.SellStatisticsMapper;
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
public class SellStatisticsServiceImpl extends CommonServiceImpl<SellStatisticsMapper, TbWhInventory> implements SellStatisticsService {

    // private final RedisUtils redisUtils;
    private final SellStatisticsMapper sellStatisticsMapper;

    @Override
    public PageInfo<SellStatisticsDto> queryAll(SellStatisticsQueryParam query, Pageable pageable) {
        IPage<SellStatisticsDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<SellStatisticsDto> page = sellStatisticsMapper.queryAll(queryPage, query);
        return ConvertUtil.convertPage(page, SellStatisticsDto.class);
    }

    @Override
    public Object getDetail(Long goodId, Pageable pageable) {
        IPage<SupplierOfferDetailDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<SupplierOfferDetailDto> page = sellStatisticsMapper.getDetail(goodId, queryPage);
        return ConvertUtil.convertPage(page, SupplierOfferDetailDto.class);
    }
}
