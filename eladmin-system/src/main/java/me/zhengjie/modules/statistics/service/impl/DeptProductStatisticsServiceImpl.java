package me.zhengjie.modules.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.DeptProductStatisticsService;
import me.zhengjie.modules.statistics.service.dto.DeptProductStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.ProductStatisticsQueryParam;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferDetailDto;
import me.zhengjie.modules.statistics.service.mapper.DeptProductStatisticsMapper;
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
public class DeptProductStatisticsServiceImpl extends CommonServiceImpl<DeptProductStatisticsMapper, TbWhInventory> implements DeptProductStatisticsService {

    // private final RedisUtils redisUtils;
    private final DeptProductStatisticsMapper deptProductStatisticsMapper;

    @Override
    public PageInfo<DeptProductStatisticsDto> queryAll(ProductStatisticsQueryParam query, Pageable pageable) {
        IPage<DeptProductStatisticsDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<DeptProductStatisticsDto> page = deptProductStatisticsMapper.queryAll(queryPage, query);
        return ConvertUtil.convertPage(page, DeptProductStatisticsDto.class);
    }

    @Override
    public PageInfo<SupplierOfferDetailDto> getDetail(Long sourceId, Pageable pageable) {
        IPage<SupplierOfferDetailDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<SupplierOfferDetailDto> page = deptProductStatisticsMapper.getDetail(queryPage, sourceId);
        return ConvertUtil.convertPage(page, SupplierOfferDetailDto.class);
    }
}
