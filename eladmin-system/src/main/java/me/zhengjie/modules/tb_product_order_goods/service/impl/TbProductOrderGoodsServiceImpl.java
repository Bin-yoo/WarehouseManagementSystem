package me.zhengjie.modules.tb_product_order_goods.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_product_order_goods.domain.TbProductOrderGoods;
import me.zhengjie.modules.tb_product_order_goods.service.TbProductOrderGoodsService;
import me.zhengjie.modules.tb_product_order_goods.service.dto.TbProductOrderGoodsDto;
import me.zhengjie.modules.tb_product_order_goods.service.dto.TbProductOrderGoodsQueryParam;
import me.zhengjie.modules.tb_product_order_goods.service.mapper.TbProductOrderGoodsMapper;
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
// @CacheConfig(cacheNames = TbProductOrderGoodsService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbProductOrderGoodsServiceImpl extends CommonServiceImpl<TbProductOrderGoodsMapper, TbProductOrderGoods> implements TbProductOrderGoodsService {

    // private final RedisUtils redisUtils;
    private final TbProductOrderGoodsMapper tbProductOrderGoodsMapper;

    @Override
    public PageInfo<TbProductOrderGoodsDto> queryAll(TbProductOrderGoodsQueryParam query, Pageable pageable) {
        IPage<TbProductOrderGoods> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbProductOrderGoods> page = tbProductOrderGoodsMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbProductOrderGoodsDto.class);
    }

    @Override
    public List<TbProductOrderGoodsDto> queryAll(TbProductOrderGoodsQueryParam query){
        return ConvertUtil.convertList(tbProductOrderGoodsMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbProductOrderGoodsDto.class);
    }

    @Override
    public TbProductOrderGoods getById(String id) {
        return tbProductOrderGoodsMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbProductOrderGoodsDto findById(String id) {
        return ConvertUtil.convert(getById(id), TbProductOrderGoodsDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbProductOrderGoodsDto resources) {
        TbProductOrderGoods entity = ConvertUtil.convert(resources, TbProductOrderGoods.class);
        return tbProductOrderGoodsMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbProductOrderGoodsDto resources){
        TbProductOrderGoods entity = ConvertUtil.convert(resources, TbProductOrderGoods.class);
        int ret = tbProductOrderGoodsMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<String> ids){
        // delCaches(ids);
        return tbProductOrderGoodsMapper.deleteBatchIds(ids);
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
    public void download(List<TbProductOrderGoodsDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbProductOrderGoodsDto tbProductOrderGoods : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("货品数量", tbProductOrderGoods.getGoodNum());
              map.put("单价", tbProductOrderGoods.getPrice());
              map.put("总价金额", tbProductOrderGoods.getTotalPrice());
              map.put("备注", tbProductOrderGoods.getRemark());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
