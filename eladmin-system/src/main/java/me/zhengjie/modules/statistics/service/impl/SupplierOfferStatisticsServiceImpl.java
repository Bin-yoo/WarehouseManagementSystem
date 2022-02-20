package me.zhengjie.modules.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.dto.InventoryDetailDto;
import me.zhengjie.modules.statistics.service.SupplierOfferStatisticsService;
import me.zhengjie.modules.statistics.service.dto.*;
import me.zhengjie.modules.statistics.service.mapper.SupplierOfferStatisticsMapper;
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
public class SupplierOfferStatisticsServiceImpl extends CommonServiceImpl<SupplierOfferStatisticsMapper, TbWhInventory> implements SupplierOfferStatisticsService {

    // private final RedisUtils redisUtils;
    private final SupplierOfferStatisticsMapper supplierOfferStatisticsMapper;

    @Override
    public PageInfo<SupplierOfferStatisticsDto> queryAll(SupplierOfferStatisticsQueryParam query, Pageable pageable) {
        IPage<SupplierOfferStatisticsDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<SupplierOfferStatisticsDto> page = supplierOfferStatisticsMapper.queryAll(queryPage, query);
        return ConvertUtil.convertPage(page, SupplierOfferStatisticsDto.class);
    }

    @Override
    public Object getDetail(Long sourceId, Pageable pageable) {
        IPage<SupplierOfferDetailDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<SupplierOfferDetailDto> page = supplierOfferStatisticsMapper.getDetail(sourceId, queryPage);
        return ConvertUtil.convertPage(page, SupplierOfferDetailDto.class);
    }
}
