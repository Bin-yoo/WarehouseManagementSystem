package me.zhengjie.modules.tb_purchase_order_goods.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_purchase_order_goods.domain.TbPurchaseOrderGoods;
import me.zhengjie.modules.tb_purchase_order_goods.service.TbPurchaseOrderGoodsService;
import me.zhengjie.modules.tb_purchase_order_goods.service.dto.TbPurchaseOrderGoodsDto;
import me.zhengjie.modules.tb_purchase_order_goods.service.dto.TbPurchaseOrderGoodsQueryParam;
import me.zhengjie.modules.tb_purchase_order_goods.service.mapper.TbPurchaseOrderGoodsMapper;
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
// @CacheConfig(cacheNames = TbPurchaseOrderGoodsService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbPurchaseOrderGoodsServiceImpl extends CommonServiceImpl<TbPurchaseOrderGoodsMapper, TbPurchaseOrderGoods> implements TbPurchaseOrderGoodsService {

    // private final RedisUtils redisUtils;
    private final TbPurchaseOrderGoodsMapper tbPurchaseOrderGoodsMapper;

    @Override
    public PageInfo<TbPurchaseOrderGoodsDto> queryAll(TbPurchaseOrderGoodsQueryParam query, Pageable pageable) {
        IPage<TbPurchaseOrderGoods> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbPurchaseOrderGoods> page = tbPurchaseOrderGoodsMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbPurchaseOrderGoodsDto.class);
    }

    @Override
    public List<TbPurchaseOrderGoodsDto> queryAll(TbPurchaseOrderGoodsQueryParam query){
        return ConvertUtil.convertList(tbPurchaseOrderGoodsMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbPurchaseOrderGoodsDto.class);
    }

    @Override
    public TbPurchaseOrderGoods getById(String id) {
        return tbPurchaseOrderGoodsMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbPurchaseOrderGoodsDto findById(String id) {
        return ConvertUtil.convert(getById(id), TbPurchaseOrderGoodsDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbPurchaseOrderGoodsDto resources) {
        TbPurchaseOrderGoods entity = ConvertUtil.convert(resources, TbPurchaseOrderGoods.class);
        return tbPurchaseOrderGoodsMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbPurchaseOrderGoodsDto resources){
        TbPurchaseOrderGoods entity = ConvertUtil.convert(resources, TbPurchaseOrderGoods.class);
        int ret = tbPurchaseOrderGoodsMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<String> ids){
        // delCaches(ids);
        return tbPurchaseOrderGoodsMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(String id){
        Set<String> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    /*
    private void delCaches(String id) {
        redisUtils.delByKey(CACHE_KEY + "::id:", id);
    }

    private void delCaches(Set<String> ids) {
        for (String id: ids) {
            delCaches(id);
        }
    }*/

    /*
    @Override
    public void download(List<TbPurchaseOrderGoodsDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbPurchaseOrderGoodsDto tbPurchaseOrderGoods : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("货品数量", tbPurchaseOrderGoods.getGoodNum());
              map.put("单价", tbPurchaseOrderGoods.getPrice());
              map.put("总价金额", tbPurchaseOrderGoods.getTotalPrice());
              map.put("备注", tbPurchaseOrderGoods.getRemark());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
