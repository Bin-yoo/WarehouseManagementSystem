package me.zhengjie.modules.inventorywarning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.inventory.domain.TbWhInventory;
import me.zhengjie.modules.inventory.service.dto.InventoryDetailDto;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryDto;
import me.zhengjie.modules.inventory.service.dto.TbWhInventoryQueryParam;
import me.zhengjie.modules.inventorywarning.service.InventoryWarningService;
import me.zhengjie.modules.inventorywarning.service.mapper.InventoryWarningMapper;
import me.zhengjie.modules.warehouse.service.dto.TbWarehouseDto;
import me.zhengjie.modules.warehouse.service.mapper.TbWarehouseMapper;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
* @author LiangBin
* @date 2021-12-01
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbWhInventoryService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class InventoryWarningServiceImpl extends CommonServiceImpl<InventoryWarningMapper, TbWhInventory> implements InventoryWarningService {

    // private final RedisUtils redisUtils;
    private final InventoryWarningMapper inventoryWarningMapper;

    @Override
    public PageInfo<TbWhInventoryDto> queryAll(TbWhInventoryQueryParam query, Pageable pageable) {
        IPage<TbWhInventoryDto> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbWhInventoryDto> page = inventoryWarningMapper.queryAll(queryPage, query);
        return ConvertUtil.convertPage(page, TbWhInventoryDto.class);
    }

    @Override
    public List<TbWhInventoryDto> queryAll(TbWhInventoryQueryParam query){
        return ConvertUtil.convertList(inventoryWarningMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbWhInventoryDto.class);
    }

    @Override
    public TbWhInventory getById(String id) {
        return inventoryWarningMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbWhInventoryDto findById(String id) {
        return ConvertUtil.convert(getById(id), TbWhInventoryDto.class);
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
