package me.zhengjie.modules.tb_purchase_order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_purchase_order.domain.TbPurchaseOrder;
import me.zhengjie.modules.tb_purchase_order.service.TbPurchaseOrderService;
import me.zhengjie.modules.tb_purchase_order.service.dto.TbPurchaseOrderDto;
import me.zhengjie.modules.tb_purchase_order.service.dto.TbPurchaseOrderQueryParam;
import me.zhengjie.modules.tb_purchase_order.service.mapper.TbPurchaseOrderMapper;
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
// @CacheConfig(cacheNames = TbPurchaseOrderService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbPurchaseOrderServiceImpl extends CommonServiceImpl<TbPurchaseOrderMapper, TbPurchaseOrder> implements TbPurchaseOrderService {

    // private final RedisUtils redisUtils;
    private final TbPurchaseOrderMapper tbPurchaseOrderMapper;

    @Override
    public PageInfo<TbPurchaseOrderDto> queryAll(TbPurchaseOrderQueryParam query, Pageable pageable) {
        IPage<TbPurchaseOrder> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbPurchaseOrder> page = tbPurchaseOrderMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbPurchaseOrderDto.class);
    }

    @Override
    public List<TbPurchaseOrderDto> queryAll(TbPurchaseOrderQueryParam query){
        return ConvertUtil.convertList(tbPurchaseOrderMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbPurchaseOrderDto.class);
    }

    @Override
    public TbPurchaseOrder getById(Long id) {
        return tbPurchaseOrderMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbPurchaseOrderDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbPurchaseOrderDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbPurchaseOrderDto resources) {
        TbPurchaseOrder entity = ConvertUtil.convert(resources, TbPurchaseOrder.class);
        return tbPurchaseOrderMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbPurchaseOrderDto resources){
        TbPurchaseOrder entity = ConvertUtil.convert(resources, TbPurchaseOrder.class);
        int ret = tbPurchaseOrderMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbPurchaseOrderMapper.deleteBatchIds(ids);
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
    public void download(List<TbPurchaseOrderDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbPurchaseOrderDto tbPurchaseOrder : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("单号", tbPurchaseOrder.getOrderNo());
              map.put("制单日期", tbPurchaseOrder.getOrderDate());
              map.put("制单人id", tbPurchaseOrder.getOrderPersonId());
              map.put("制单人名称", tbPurchaseOrder.getOrderPerson());
              map.put("入库日期", tbPurchaseOrder.getInDate());
              map.put("存入仓库id", tbPurchaseOrder.getInWhId());
              map.put("存入仓库名称", tbPurchaseOrder.getInWhName());
              map.put("供应商id", tbPurchaseOrder.getSupplierId());
              map.put("供应商名称", tbPurchaseOrder.getSupplier());
              map.put("原始单号", tbPurchaseOrder.getOriginOrderNo());
              map.put("备注", tbPurchaseOrder.getRemark());
              map.put("采购单状态 1 未审批 2 已通过 3 未通过", tbPurchaseOrder.getStatus());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
