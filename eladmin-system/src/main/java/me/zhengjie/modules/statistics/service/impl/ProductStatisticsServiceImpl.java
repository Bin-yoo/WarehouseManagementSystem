package me.zhengjie.modules.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.ProductStatisticsService;
import me.zhengjie.modules.statistics.service.dto.ProductStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.ProductStatisticsQueryParam;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferDetailDto;
import me.zhengjie.modules.statistics.service.mapper.ProductStatisticsMapper;
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
public class ProductStatisticsServiceImpl extends CommonServiceImpl<ProductStatisticsMapper, TbWhInventory> implements ProductStatisticsService {

    // private final RedisUtils redisUtils;
    private final ProductStatisticsMapper productStatisticsMapper;

    @Override
    public PageInfo<ProductStatisticsDto> queryAll(ProductStatisticsQueryParam query, Pageable pageable) {
        IPage<ProductStatisticsDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<ProductStatisticsDto> page = productStatisticsMapper.queryAll(queryPage, query);
        return ConvertUtil.convertPage(page, ProductStatisticsDto.class);
    }

    @Override
    public Object getDetail(Long goodId, Pageable pageable) {
        IPage<SupplierOfferDetailDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<SupplierOfferDetailDto> page = productStatisticsMapper.getDetail(goodId, queryPage);
        return ConvertUtil.convertPage(page, SupplierOfferDetailDto.class);
    }
}
