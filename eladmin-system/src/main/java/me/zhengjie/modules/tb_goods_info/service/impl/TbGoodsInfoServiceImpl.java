package me.zhengjie.modules.tb_goods_info.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_goods_info.domain.TbGoodsInfo;
import me.zhengjie.modules.tb_goods_info.service.TbGoodsInfoService;
import me.zhengjie.modules.tb_goods_info.service.dto.TbGoodsInfoDto;
import me.zhengjie.modules.tb_goods_info.service.dto.TbGoodsInfoQueryParam;
import me.zhengjie.modules.tb_goods_info.service.mapper.TbGoodsInfoMapper;
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
// @CacheConfig(cacheNames = TbGoodsInfoService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbGoodsInfoServiceImpl extends CommonServiceImpl<TbGoodsInfoMapper, TbGoodsInfo> implements TbGoodsInfoService {

    // private final RedisUtils redisUtils;
    private final TbGoodsInfoMapper tbGoodsInfoMapper;

    @Override
    public PageInfo<TbGoodsInfoDto> queryAll(TbGoodsInfoQueryParam query, Pageable pageable) {
        IPage<TbGoodsInfo> queryPage = PageUtil.toMybatisPage(pageable);
        IPage<TbGoodsInfo> page = tbGoodsInfoMapper.selectPage(queryPage, QueryHelpMybatisPlus.getPredicate(query));
        return ConvertUtil.convertPage(page, TbGoodsInfoDto.class);
    }

    @Override
    public List<TbGoodsInfoDto> queryAll(TbGoodsInfoQueryParam query){
        return ConvertUtil.convertList(tbGoodsInfoMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbGoodsInfoDto.class);
    }

    @Override
    public TbGoodsInfo getById(Long id) {
        return tbGoodsInfoMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbGoodsInfoDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbGoodsInfoDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbGoodsInfoDto resources) {
        TbGoodsInfo entity = ConvertUtil.convert(resources, TbGoodsInfo.class);
        return tbGoodsInfoMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbGoodsInfoDto resources){
        TbGoodsInfo entity = ConvertUtil.convert(resources, TbGoodsInfo.class);
        int ret = tbGoodsInfoMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbGoodsInfoMapper.deleteBatchIds(ids);
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
    public void download(List<TbGoodsInfoDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbGoodsInfoDto tbGoodsInfo : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("货品编码", tbGoodsInfo.getGCode());
              map.put("货品名称", tbGoodsInfo.getGName());
              map.put("规格", tbGoodsInfo.getSpecification());
              map.put("拼音码", tbGoodsInfo.getPyCode());
              map.put("单位", tbGoodsInfo.getGUnit());
              map.put("货品类别", tbGoodsInfo.getGType());
              map.put("进价", tbGoodsInfo.getPPrice());
              map.put("售价", tbGoodsInfo.getSPrice());
              map.put("库存上限", tbGoodsInfo.getULimit());
              map.put("库存下限", tbGoodsInfo.getLLimit());
              map.put("货位", tbGoodsInfo.getGAllocation());
              map.put("生产厂家", tbGoodsInfo.getManufacturer());
              map.put("备注", tbGoodsInfo.getRemark());
              map.put("批次", tbGoodsInfo.getBatch());
              map.put("生产日期", tbGoodsInfo.getProductDate());
              map.put("有效日期", tbGoodsInfo.getValidDate());
              map.put("物资来源", tbGoodsInfo.getSource());
              map.put("条形码", tbGoodsInfo.getBarCode());
              map.put("二维码", tbGoodsInfo.getQrCode());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
