package me.zhengjie.modules.tb_return_order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_return_order.domain.TbReturnOrder;
import me.zhengjie.modules.tb_return_order.service.TbReturnOrderService;
import me.zhengjie.modules.tb_return_order.service.dto.TbReturnOrderDto;
import me.zhengjie.modules.tb_return_order.service.dto.TbReturnOrderQueryParam;
import me.zhengjie.modules.tb_return_order.service.mapper.TbReturnOrderMapper;
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
// @CacheConfig(cacheNames = TbReturnOrderService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbReturnOrderServiceImpl extends CommonServiceImpl<TbReturnOrderMapper, TbReturnOrder> implements TbReturnOrderService {

    // private final RedisUtils redisUtils;
    private final TbReturnOrderMapper tbReturnOrderMapper;

    @Override
    public PageInfo<TbReturnOrderDto> queryAll(TbReturnOrderQueryParam query, Pageable pageable) {
        IPage<TbReturnOrder> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbReturnOrder> page = tbReturnOrderMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbReturnOrderDto.class);
    }

    @Override
    public List<TbReturnOrderDto> queryAll(TbReturnOrderQueryParam query){
        return ConvertUtil.convertList(tbReturnOrderMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbReturnOrderDto.class);
    }

    @Override
    public TbReturnOrder getById(Long id) {
        return tbReturnOrderMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbReturnOrderDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbReturnOrderDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbReturnOrderDto resources) {
        TbReturnOrder entity = ConvertUtil.convert(resources, TbReturnOrder.class);
        return tbReturnOrderMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbReturnOrderDto resources){
        TbReturnOrder entity = ConvertUtil.convert(resources, TbReturnOrder.class);
        int ret = tbReturnOrderMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbReturnOrderMapper.deleteBatchIds(ids);
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
    public void download(List<TbReturnOrderDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbReturnOrderDto tbReturnOrder : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("单号", tbReturnOrder.getOrderNo());
              map.put("制单日期", tbReturnOrder.getOrderDate());
              map.put("制单人id", tbReturnOrder.getOrderPersonId());
              map.put("制单人名称", tbReturnOrder.getOrderPerson());
              map.put("入库日期", tbReturnOrder.getInDate());
              map.put("存入仓库id", tbReturnOrder.getInWhId());
              map.put("存入仓库名称", tbReturnOrder.getInWhName());
              map.put("客户id", tbReturnOrder.getCustomerId());
              map.put("客户名称", tbReturnOrder.getCustomer());
              map.put("原始单号", tbReturnOrder.getOriginOrderNo());
              map.put("备注", tbReturnOrder.getRemark());
              map.put("采购单状态 1 未审批 2 已通过 3 未通过", tbReturnOrder.getStatus());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
