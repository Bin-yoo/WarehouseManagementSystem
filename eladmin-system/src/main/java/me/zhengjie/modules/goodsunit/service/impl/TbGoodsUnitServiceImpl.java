package me.zhengjie.modules.goodsunit.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.goodsunit.domain.TbGoodsUnit;
import me.zhengjie.modules.goodsunit.service.TbGoodsUnitService;
import me.zhengjie.modules.goodsunit.service.dto.TbGoodsUnitDto;
import me.zhengjie.modules.goodsunit.service.dto.TbGoodsUnitQueryParam;
import me.zhengjie.modules.goodsunit.service.mapper.TbGoodsUnitMapper;
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
// @CacheConfig(cacheNames = TbGoodsUnitService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbGoodsUnitServiceImpl extends CommonServiceImpl<TbGoodsUnitMapper, TbGoodsUnit> implements TbGoodsUnitService {

    // private final RedisUtils redisUtils;
    private final TbGoodsUnitMapper tbGoodsUnitMapper;

    @Override
    public PageInfo<TbGoodsUnitDto> queryAll(TbGoodsUnitQueryParam query, Pageable pageable) {
        IPage<TbGoodsUnit> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbGoodsUnit> page = tbGoodsUnitMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbGoodsUnitDto.class);
    }

    @Override
    public List<TbGoodsUnitDto> queryAll(TbGoodsUnitQueryParam query){
        return ConvertUtil.convertList(tbGoodsUnitMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbGoodsUnitDto.class);
    }

    @Override
    public TbGoodsUnit getById(Long id) {
        return tbGoodsUnitMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbGoodsUnitDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbGoodsUnitDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbGoodsUnitDto resources) {
        TbGoodsUnit entity = ConvertUtil.convert(resources, TbGoodsUnit.class);
        if (entity.getSort()==null || "".equals(entity.getSort())) {
            Integer count = tbGoodsUnitMapper.selectCount(null);
            entity.setSort(count + 1);
        }
        return tbGoodsUnitMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbGoodsUnitDto resources){
        TbGoodsUnit entity = ConvertUtil.convert(resources, TbGoodsUnit.class);
        int ret = tbGoodsUnitMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbGoodsUnitMapper.deleteBatchIds(ids);
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
    public void download(List<TbGoodsUnitDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbGoodsUnitDto tbGoodsUnit : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("货品单位名称", tbGoodsUnit.getGuName());
              map.put("排序", tbGoodsUnit.getSort());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
