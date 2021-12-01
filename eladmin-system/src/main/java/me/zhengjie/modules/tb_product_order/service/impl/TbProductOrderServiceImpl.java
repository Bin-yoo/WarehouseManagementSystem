package me.zhengjie.modules.tb_product_order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_product_order.domain.TbProductOrder;
import me.zhengjie.modules.tb_product_order.service.TbProductOrderService;
import me.zhengjie.modules.tb_product_order.service.dto.TbProductOrderDto;
import me.zhengjie.modules.tb_product_order.service.dto.TbProductOrderQueryParam;
import me.zhengjie.modules.tb_product_order.service.mapper.TbProductOrderMapper;
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
// @CacheConfig(cacheNames = TbProductOrderService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbProductOrderServiceImpl extends CommonServiceImpl<TbProductOrderMapper, TbProductOrder> implements TbProductOrderService {

    // private final RedisUtils redisUtils;
    private final TbProductOrderMapper tbProductOrderMapper;

    @Override
    public PageInfo<TbProductOrderDto> queryAll(TbProductOrderQueryParam query, Pageable pageable) {
        IPage<TbProductOrder> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbProductOrder> page = tbProductOrderMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbProductOrderDto.class);
    }

    @Override
    public List<TbProductOrderDto> queryAll(TbProductOrderQueryParam query){
        return ConvertUtil.convertList(tbProductOrderMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbProductOrderDto.class);
    }

    @Override
    public TbProductOrder getById(Long id) {
        return tbProductOrderMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbProductOrderDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbProductOrderDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbProductOrderDto resources) {
        TbProductOrder entity = ConvertUtil.convert(resources, TbProductOrder.class);
        return tbProductOrderMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbProductOrderDto resources){
        TbProductOrder entity = ConvertUtil.convert(resources, TbProductOrder.class);
        int ret = tbProductOrderMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbProductOrderMapper.deleteBatchIds(ids);
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
    public void download(List<TbProductOrderDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbProductOrderDto tbProductOrder : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("单号", tbProductOrder.getOrderNo());
              map.put("制单日期", tbProductOrder.getOrderDate());
              map.put("制单人id", tbProductOrder.getOrderPersonId());
              map.put("制单人名称", tbProductOrder.getOrderPerson());
              map.put("入库日期", tbProductOrder.getInDate());
              map.put("存入仓库id", tbProductOrder.getInWhId());
              map.put("存入仓库名称", tbProductOrder.getInWhName());
              map.put("生产部门id", tbProductOrder.getDeptId());
              map.put("生产部门名称", tbProductOrder.getDeptName());
              map.put("原始单号", tbProductOrder.getOriginOrderNo());
              map.put("备注", tbProductOrder.getRemark());
              map.put("采购单状态 1 未审批 2 已通过 3 未通过", tbProductOrder.getStatus());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
