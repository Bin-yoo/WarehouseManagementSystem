package me.zhengjie.modules.tb_return_order_goods.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_return_order_goods.domain.TbReturnOrderGoods;
import me.zhengjie.modules.tb_return_order_goods.service.TbReturnOrderGoodsService;
import me.zhengjie.modules.tb_return_order_goods.service.dto.TbReturnOrderGoodsDto;
import me.zhengjie.modules.tb_return_order_goods.service.dto.TbReturnOrderGoodsQueryParam;
import me.zhengjie.modules.tb_return_order_goods.service.mapper.TbReturnOrderGoodsMapper;
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
// @CacheConfig(cacheNames = TbReturnOrderGoodsService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbReturnOrderGoodsServiceImpl extends CommonServiceImpl<TbReturnOrderGoodsMapper, TbReturnOrderGoods> implements TbReturnOrderGoodsService {

    // private final RedisUtils redisUtils;
    private final TbReturnOrderGoodsMapper tbReturnOrderGoodsMapper;

    @Override
    public PageInfo<TbReturnOrderGoodsDto> queryAll(TbReturnOrderGoodsQueryParam query, Pageable pageable) {
        IPage<TbReturnOrderGoods> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbReturnOrderGoods> page = tbReturnOrderGoodsMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbReturnOrderGoodsDto.class);
    }

    @Override
    public List<TbReturnOrderGoodsDto> queryAll(TbReturnOrderGoodsQueryParam query){
        return ConvertUtil.convertList(tbReturnOrderGoodsMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbReturnOrderGoodsDto.class);
    }

    @Override
    public TbReturnOrderGoods getById(String id) {
        return tbReturnOrderGoodsMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbReturnOrderGoodsDto findById(String id) {
        return ConvertUtil.convert(getById(id), TbReturnOrderGoodsDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbReturnOrderGoodsDto resources) {
        TbReturnOrderGoods entity = ConvertUtil.convert(resources, TbReturnOrderGoods.class);
        return tbReturnOrderGoodsMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbReturnOrderGoodsDto resources){
        TbReturnOrderGoods entity = ConvertUtil.convert(resources, TbReturnOrderGoods.class);
        int ret = tbReturnOrderGoodsMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<String> ids){
        // delCaches(ids);
        return tbReturnOrderGoodsMapper.deleteBatchIds(ids);
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
    public void download(List<TbReturnOrderGoodsDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbReturnOrderGoodsDto tbReturnOrderGoods : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("货品数量", tbReturnOrderGoods.getGoodNum());
              map.put("单价", tbReturnOrderGoods.getPrice());
              map.put("总价金额", tbReturnOrderGoods.getTotalPrice());
              map.put("备注", tbReturnOrderGoods.getRemark());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
