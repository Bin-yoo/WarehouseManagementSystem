package me.zhengjie.modules.tb_goods_type.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_goods_type.domain.TbGoodsType;
import me.zhengjie.modules.tb_goods_type.service.TbGoodsTypeService;
import me.zhengjie.modules.tb_goods_type.service.dto.TbGoodsTypeDto;
import me.zhengjie.modules.tb_goods_type.service.dto.TbGoodsTypeQueryParam;
import me.zhengjie.modules.tb_goods_type.service.mapper.TbGoodsTypeMapper;
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
// @CacheConfig(cacheNames = TbGoodsTypeService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbGoodsTypeServiceImpl extends CommonServiceImpl<TbGoodsTypeMapper, TbGoodsType> implements TbGoodsTypeService {

    // private final RedisUtils redisUtils;
    private final TbGoodsTypeMapper tbGoodsTypeMapper;

    @Override
    public PageInfo<TbGoodsTypeDto> queryAll(TbGoodsTypeQueryParam query, Pageable pageable) {
        IPage<TbGoodsType> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbGoodsType> page = tbGoodsTypeMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbGoodsTypeDto.class);
    }

    @Override
    public List<TbGoodsTypeDto> queryAll(TbGoodsTypeQueryParam query){
        return ConvertUtil.convertList(tbGoodsTypeMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbGoodsTypeDto.class);
    }

    @Override
    public TbGoodsType getById(Long id) {
        return tbGoodsTypeMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbGoodsTypeDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbGoodsTypeDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbGoodsTypeDto resources) {
        TbGoodsType entity = ConvertUtil.convert(resources, TbGoodsType.class);
        return tbGoodsTypeMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbGoodsTypeDto resources){
        TbGoodsType entity = ConvertUtil.convert(resources, TbGoodsType.class);
        int ret = tbGoodsTypeMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbGoodsTypeMapper.deleteBatchIds(ids);
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
    public void download(List<TbGoodsTypeDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbGoodsTypeDto tbGoodsType : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("货品类型名称", tbGoodsType.getGtName());
              map.put("父级id", tbGoodsType.getParentId());
              map.put("是否文件夹", tbGoodsType.getIsFolder());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
