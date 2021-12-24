package me.zhengjie.modules.tb_wh_goods.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_wh_goods.domain.TbWhGoods;
import me.zhengjie.modules.tb_wh_goods.service.TbWhGoodsService;
import me.zhengjie.modules.tb_wh_goods.service.dto.TbWhGoodsDto;
import me.zhengjie.modules.tb_wh_goods.service.dto.TbWhGoodsQueryParam;
import me.zhengjie.modules.tb_wh_goods.service.mapper.TbWhGoodsMapper;
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
* @date 2021-12-21
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbWhGoodsService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbWhGoodsServiceImpl extends CommonServiceImpl<TbWhGoodsMapper, TbWhGoods> implements TbWhGoodsService {

    // private final RedisUtils redisUtils;
    private final TbWhGoodsMapper tbWhGoodsMapper;

    @Override
    public PageInfo<TbWhGoodsDto> queryAll(TbWhGoodsQueryParam query, Pageable pageable) {
        IPage<TbWhGoods> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbWhGoods> page = tbWhGoodsMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbWhGoodsDto.class);
    }

    @Override
    public List<TbWhGoodsDto> queryAll(TbWhGoodsQueryParam query){
        return ConvertUtil.convertList(tbWhGoodsMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbWhGoodsDto.class);
    }

    @Override
    public TbWhGoods getById(Long id) {
        return tbWhGoodsMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbWhGoodsDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbWhGoodsDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbWhGoodsDto resources) {
        TbWhGoods entity = ConvertUtil.convert(resources, TbWhGoods.class);
        return tbWhGoodsMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbWhGoodsDto resources){
        TbWhGoods entity = ConvertUtil.convert(resources, TbWhGoods.class);
        int ret = tbWhGoodsMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbWhGoodsMapper.deleteBatchIds(ids);
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
    public void download(List<TbWhGoodsDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbWhGoodsDto tbWhGoods : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("货品id", tbWhGoods.getGoodId());
              map.put("仓库id", tbWhGoods.getWhId());
              map.put("仓初数量", tbWhGoods.getInitialCount());
              map.put("库存下限", tbWhGoods.getLowerLimit());
              map.put("库存上限", tbWhGoods.getUpperLimit());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
