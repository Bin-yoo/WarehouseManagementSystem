package me.zhengjie.modules.statistics.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.statistics.service.TransferStatisticsService;
import me.zhengjie.modules.statistics.service.dto.ProductStatisticsDto;
import me.zhengjie.modules.statistics.service.dto.ProductStatisticsQueryParam;
import me.zhengjie.modules.statistics.service.dto.SupplierOfferDetailDto;
import me.zhengjie.modules.statistics.service.dto.TransferStatisticsDto;
import me.zhengjie.modules.statistics.service.mapper.TransferStatisticsMapper;
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
public class TransferStatisticsServiceImpl extends CommonServiceImpl<TransferStatisticsMapper, TbWhInventory> implements TransferStatisticsService {

    // private final RedisUtils redisUtils;
    private final TransferStatisticsMapper transferStatisticsMapper;

    @Override
    public PageInfo<TransferStatisticsDto> queryAll(ProductStatisticsQueryParam query, Pageable pageable) {
        IPage<TransferStatisticsDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TransferStatisticsDto> page = transferStatisticsMapper.queryAll(queryPage, query);
        return ConvertUtil.convertPage(page, TransferStatisticsDto.class);
    }

    @Override
    public Object getDetail(Long goodId, Pageable pageable) {
        IPage<SupplierOfferDetailDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<SupplierOfferDetailDto> page = transferStatisticsMapper.getDetail(goodId, queryPage);
        return ConvertUtil.convertPage(page, SupplierOfferDetailDto.class);
    }
}
