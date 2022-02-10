package me.zhengjie.modules.tb_order_goods.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_order_goods.domain.TbOrderGoods;
import me.zhengjie.modules.tb_order_goods.service.TbOrderGoodsService;
import me.zhengjie.modules.tb_order_goods.service.dto.TbOrderGoodsDto;
import me.zhengjie.modules.tb_order_goods.service.dto.TbOrderGoodsQueryParam;
import me.zhengjie.modules.tb_order_goods.service.mapper.TbOrderGoodsMapper;
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
* @date 2022-02-10
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbOrderGoodsService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbOrderGoodsServiceImpl extends CommonServiceImpl<TbOrderGoodsMapper, TbOrderGoods> implements TbOrderGoodsService {

    // private final RedisUtils redisUtils;
    private final TbOrderGoodsMapper tbOrderGoodsMapper;

    @Override
    public PageInfo<TbOrderGoodsDto> queryAll(TbOrderGoodsQueryParam query, Pageable pageable) {
        IPage<TbOrderGoods> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbOrderGoods> page = tbOrderGoodsMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbOrderGoodsDto.class);
    }

    @Override
    public List<TbOrderGoodsDto> queryAll(TbOrderGoodsQueryParam query){
        return ConvertUtil.convertList(tbOrderGoodsMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbOrderGoodsDto.class);
    }

    @Override
    public TbOrderGoods getById(Long id) {
        return tbOrderGoodsMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbOrderGoodsDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbOrderGoodsDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbOrderGoodsDto resources) {
        TbOrderGoods entity = ConvertUtil.convert(resources, TbOrderGoods.class);
        return tbOrderGoodsMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbOrderGoodsDto resources){
        TbOrderGoods entity = ConvertUtil.convert(resources, TbOrderGoods.class);
        int ret = tbOrderGoodsMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbOrderGoodsMapper.deleteBatchIds(ids);
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
    public void download(List<TbOrderGoodsDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbOrderGoodsDto tbOrderGoods : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("货品数量", tbOrderGoods.getGoodNum());
              map.put("单价", tbOrderGoods.getPrice());
              map.put("总价金额", tbOrderGoods.getTotalPrice());
              map.put("批次", tbOrderGoods.getBatch());
              map.put("生产日期", tbOrderGoods.getProductDate());
              map.put("有效日期", tbOrderGoods.getValidDate());
              map.put("备注", tbOrderGoods.getRemark());
              map.put("账面数量", tbOrderGoods.getPaperGoodNum());
              map.put("实盘数量", tbOrderGoods.getInventoryGoodNum());
              map.put("盈亏数量", tbOrderGoods.getPlGoodNum());
              map.put("盈亏金额", tbOrderGoods.getPlPrice());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
