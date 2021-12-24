package me.zhengjie.modules.warehouse.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.query.MPJQueryWrapper;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.employee.domain.TbEmployee;
import me.zhengjie.modules.employee.domain.vo.TbEmployeeVo;
import me.zhengjie.modules.employee.service.mapper.TbEmployeeMapper;
import me.zhengjie.modules.warehouse.domain.vo.TbWarehouseVo;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.warehouse.domain.TbWarehouse;
import me.zhengjie.modules.warehouse.service.TbWarehouseService;
import me.zhengjie.modules.warehouse.service.dto.TbWarehouseDto;
import me.zhengjie.modules.warehouse.service.dto.TbWarehouseQueryParam;
import me.zhengjie.modules.warehouse.service.mapper.TbWarehouseMapper;
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
// @CacheConfig(cacheNames = TbWarehouseService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbWarehouseServiceImpl extends CommonServiceImpl<TbWarehouseMapper, TbWarehouse> implements TbWarehouseService {

    // private final RedisUtils redisUtils;
    private final TbWarehouseMapper tbWarehouseMapper;
    private final TbEmployeeMapper tbEmployeeMapper;

    @Override
    public PageInfo<TbWarehouseVo> queryAll(TbWarehouseQueryParam query, Pageable pageable) {
        IPage<TbWarehouse> queryPage = PageUtil.toMybatisPage(pageable);
        //QueryWrapper queryWrapper = new QueryWrapper<TbWarehouseDto>();
        //IPage<TbWarehouseDto> page = tbWarehouseMapper.queryAll(queryPage, queryWrapper);
        MPJQueryWrapper<TbWarehouse> mpjQueryWrapper = new MPJQueryWrapper<>();
        mpjQueryWrapper.selectAll(TbWarehouse.class)
                .select("k.name as keeper")
                .select("d.name as director")
                .select("dept.name as dept_name")
                .leftJoin("tb_employee k on t.keeper_id = k.id")
                .leftJoin("tb_employee d on t.director_id = d.id")
                .leftJoin("sys_dept dept on t.dept_id = dept.dept_id");
        if (StringUtils.isNotEmpty(query.getWhName())) {
            mpjQueryWrapper.like("t.wh_name", query.getWhName());
        }
        if (query.getDeptId() != null) {
            mpjQueryWrapper.eq("t.dept_id", query.getDeptId());
        }
        if (StringUtils.isNotEmpty(query.getKeeper())) {
            mpjQueryWrapper.like("k.name", query.getKeeper());
        }
        if (StringUtils.isNotEmpty(query.getDirector())) {
            mpjQueryWrapper.like("d.name", query.getDirector());
        }

        IPage<TbWarehouseVo> page = tbWarehouseMapper.selectJoinPage(queryPage, TbWarehouseVo.class ,mpjQueryWrapper);

        return ConvertUtil.convertPage(page, TbWarehouseVo.class);
    }

    @Override
    public List<TbWarehouseDto> queryAll(TbWarehouseQueryParam query){
        return ConvertUtil.convertList(tbWarehouseMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbWarehouseDto.class);
    }

    @Override
    public TbWarehouse getById(Long id) {
        return tbWarehouseMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbWarehouseDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbWarehouseDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbWarehouseDto resources) {
        TbWarehouse entity = ConvertUtil.convert(resources, TbWarehouse.class);
        return tbWarehouseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbWarehouseDto resources){
        TbWarehouse entity = ConvertUtil.convert(resources, TbWarehouse.class);
        int ret = tbWarehouseMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbWarehouseMapper.deleteBatchIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Long id){
        Set<Long> set = new HashSet<>(1);
        set.add(id);
        return this.removeByIds(set);
    }

    @Override
    public Object getQueryParamAdviceList(String name) {
        //QueryWrapper<TbEmployee> wrapper = new QueryWrapper<>();
        //wrapper.like("name", name);
        return tbEmployeeMapper.lambdaQuery().like(TbEmployee::getName, name).list();
    }

    @Override
    public Object getEmpSelect() {
        return ConvertUtil.convertList(tbEmployeeMapper.selectList(null), TbEmployeeVo.class);
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
    public void download(List<TbWarehouseDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbWarehouseDto tbWarehouse : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("仓库名称", tbWarehouse.getWhName());
              map.put("所属部门", tbWarehouse.getDeptId());
              map.put("库管", tbWarehouse.getKeeper());
              map.put("负责人", tbWarehouse.getDirector());
              map.put("联系电话", tbWarehouse.getPhone());
              map.put("地址", tbWarehouse.getAddress());
              map.put("存货分类", tbWarehouse.getGoodsType());
              map.put("备注", tbWarehouse.getRemark());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
