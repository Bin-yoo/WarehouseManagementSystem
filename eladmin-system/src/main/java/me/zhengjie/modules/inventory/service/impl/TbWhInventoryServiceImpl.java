package me.zhengjie.modules.inventory.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.inventory.service.dto.InventoryDetailDto;
import me.zhengjie.modules.warehouse.service.dto.TbWarehouseDto;
import me.zhengjie.modules.warehouse.service.mapper.TbWarehouseMapper;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.TbWhInventoryService;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryDto;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryQueryParam;
import me.zhengjie.modules.inventory.service.mapper.TbWhInventoryMapper;
import me.zhengjie.utils.StringUtils;
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
// @CacheConfig(cacheNames = TbWhInventoryService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbWhInventoryServiceImpl extends CommonServiceImpl<TbWhInventoryMapper, TbWhInventory> implements TbWhInventoryService {

    // private final RedisUtils redisUtils;
    private final TbWhInventoryMapper tbWhInventoryMapper;
    private final TbWarehouseMapper tbWarehouseMapper;

    @Override
    public PageInfo<TbWhInventoryDto> queryAll(TbWhInventoryQueryParam query, Pageable pageable) {
        IPage<TbWhInventoryDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbWhInventoryDto> page;
        QueryWrapper<TbWhInventoryDto> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getGName())) {
            wrapper.like("g.g_name", query.getGName());
        }
        if (StringUtils.isNotEmpty(query.getGCode())) {
            wrapper.like("g.g_code", query.getGCode());
        }
        if (StringUtils.isNotEmpty(query.getPyCode())) {
            wrapper.like("g.py_code", query.getPyCode());
        }
        if (query.getTypeId() != null) {
            wrapper.like("t.id", query.getTypeId());
        }
        if (query.getUnitId() != null) {
            wrapper.like("u.id", query.getUnitId());
        }
        if (query.getWhId() != null) {
            wrapper.eq("w.wh_id", query.getWhId());
            ArrayList<String> list = new ArrayList<>();
            list.add("g.id");
            list.add("w.wh_id");
            wrapper.groupBy(list);
            wrapper.orderByDesc("g.g_name");
            page = tbWhInventoryMapper.queryByWareHouse(queryPage, wrapper);
        } else {
            wrapper.groupBy("g.id");
            wrapper.orderByDesc("g.g_name");
            page = tbWhInventoryMapper.queryAllWareHouse(queryPage, wrapper);
        }
        return ConvertUtil.convertPage(page, TbWhInventoryDto.class);
    }

    @Override
    public List<TbWhInventoryDto> queryAll(TbWhInventoryQueryParam query){
        return ConvertUtil.convertList(tbWhInventoryMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbWhInventoryDto.class);
    }

    @Override
    public TbWhInventory getById(String id) {
        return tbWhInventoryMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbWhInventoryDto findById(String id) {
        return ConvertUtil.convert(getById(id), TbWhInventoryDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbWhInventoryDto resources) {
        TbWhInventory entity = ConvertUtil.convert(resources, TbWhInventory.class);
        return tbWhInventoryMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbWhInventoryDto resources){
        TbWhInventory entity = ConvertUtil.convert(resources, TbWhInventory.class);
        int ret = tbWhInventoryMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<String> ids){
        // delCaches(ids);
        return tbWhInventoryMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(String id){
        Set<String> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    @Override
    public Object getWareHouseSelect() {
        return ConvertUtil.convertList(tbWarehouseMapper.lambdaQuery().list(), TbWarehouseDto.class);
    }

    @Override
    public PageInfo<InventoryDetailDto> getWhInOutDetail(Long goodId, Pageable pageable) {
        IPage<InventoryDetailDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<InventoryDetailDto> page = tbWhInventoryMapper.getWhInOutDetail(goodId, queryPage);
        return ConvertUtil.convertPage(page, InventoryDetailDto.class);
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
    public void download(List<TbWhInventoryDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbWhInventoryDto tbWhInventory : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("货品数量", tbWhInventory.getCount());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
