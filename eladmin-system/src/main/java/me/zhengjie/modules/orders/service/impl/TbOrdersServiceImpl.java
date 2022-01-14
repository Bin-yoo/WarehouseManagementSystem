package me.zhengjie.modules.orders.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.orders.domain.TbOrders;
import me.zhengjie.modules.orders.service.TbOrdersService;
import me.zhengjie.modules.orders.service.dto.TbOrdersDto;
import me.zhengjie.modules.orders.service.dto.TbOrdersQueryParam;
import me.zhengjie.modules.orders.service.mapper.TbOrdersMapper;
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
* @date 2022-01-03
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbOrdersService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbOrdersServiceImpl extends CommonServiceImpl<TbOrdersMapper, TbOrders> implements TbOrdersService {

    // private final RedisUtils redisUtils;
    private final TbOrdersMapper tbOrdersMapper;

    @Override
    public PageInfo<TbOrdersDto> queryAll(TbOrdersQueryParam query, Pageable pageable) {
        IPage<TbOrders> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbOrders> page = tbOrdersMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbOrdersDto.class);
    }

    @Override
    public List<TbOrdersDto> queryAll(TbOrdersQueryParam query){
        return ConvertUtil.convertList(tbOrdersMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbOrdersDto.class);
    }

    @Override
    public TbOrders getById(Long id) {
        return tbOrdersMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbOrdersDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbOrdersDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbOrdersDto resources) {
        TbOrders entity = ConvertUtil.convert(resources, TbOrders.class);
        return tbOrdersMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbOrdersDto resources){
        TbOrders entity = ConvertUtil.convert(resources, TbOrders.class);
        int ret = tbOrdersMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbOrdersMapper.deleteBatchIds(ids);
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
    public void download(List<TbOrdersDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbOrdersDto tbOrders : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("单据类型", tbOrders.getOrderType());
              map.put("单号", tbOrders.getOrderNo());
              map.put("制单日期", tbOrders.getOrderDate());
              map.put("制单人id", tbOrders.getOrderPersonId());
              map.put("制单人名称", tbOrders.getOrderPerson());
              map.put("经办人id", tbOrders.getManagerId());
              map.put("经办人名称", tbOrders.getManager());
              map.put("入库日期", tbOrders.getDate());
              map.put("存入仓库id", tbOrders.getWhId());
              map.put("存入仓库名称", tbOrders.getWhName());
              map.put("货品来源id(来往单位或部门id)", tbOrders.getSourceId());
              map.put("货品来源(来往单位、部门或仓库名称)", tbOrders.getSourceName());
              map.put("原始单号", tbOrders.getOriginOrderNo());
              map.put("采购单状态 1 未审批 2 已通过 3 反审核", tbOrders.getStatus());
              map.put("审核日期", tbOrders.getVerifyDate());
              map.put("审核人id", tbOrders.getVerifyPersonId());
              map.put("审核人名称", tbOrders.getVerifyPerson());
              map.put("删除标识", tbOrders.getDelFlag());
              map.put("最后修改日期", tbOrders.getUpdateTime());
              map.put("最后修改人", tbOrders.getUpdateBy());
              map.put("备注", tbOrders.getRemark());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
