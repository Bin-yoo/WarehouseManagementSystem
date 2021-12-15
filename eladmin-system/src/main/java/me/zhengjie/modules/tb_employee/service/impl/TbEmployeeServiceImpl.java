package me.zhengjie.modules.tb_employee.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.AllArgsConstructor;
import me.zhengjie.base.PageInfo;
import me.zhengjie.base.QueryHelpMybatisPlus;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.service.mapper.JobMapper;
import me.zhengjie.modules.tb_employee.domain.vo.TbEmployeeVo;
import me.zhengjie.utils.ConvertUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.modules.tb_employee.domain.TbEmployee;
import me.zhengjie.modules.tb_employee.service.TbEmployeeService;
import me.zhengjie.modules.tb_employee.service.dto.TbEmployeeDto;
import me.zhengjie.modules.tb_employee.service.dto.TbEmployeeQueryParam;
import me.zhengjie.modules.tb_employee.service.mapper.TbEmployeeMapper;
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
* @date 2021-12-08
*/
@Service
@AllArgsConstructor
// @CacheConfig(cacheNames = TbEmployeeService.CACHE_KEY)
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class TbEmployeeServiceImpl extends CommonServiceImpl<TbEmployeeMapper, TbEmployee> implements TbEmployeeService {

    // private final RedisUtils redisUtils;
    private final TbEmployeeMapper tbEmployeeMapper;
    private final JobMapper jobMapper;

    @Override
    public PageInfo<TbEmployeeVo> queryAll(TbEmployeeQueryParam query, Pageable pageable) {
        IPage<TbEmployee> queryPage = PageUtil.toMybatisPage(pageable);
        MPJLambdaWrapper<TbEmployeeDto> mpjLambdaWrapper = new MPJLambdaWrapper<>();
        mpjLambdaWrapper.selectAll(TbEmployee.class)
                .selectAs(Dept::getName, "deptName")
                .selectAs(Job::getName, "jobName")
                .leftJoin(Dept.class, Dept::getId, TbEmployee::getDeptId)
                .leftJoin(Job.class, Job::getId, TbEmployee::getJobId);

        if (StringUtils.isNotEmpty(query.getName())) {
            mpjLambdaWrapper.like(TbEmployee::getName, query.getName());
        }
        if (query.getDeptId() != null) {
            mpjLambdaWrapper.eq(TbEmployee::getDeptId, query.getDeptId());
        }
        mpjLambdaWrapper.orderByDesc(TbEmployee::getId);
        IPage<TbEmployeeDto> page = tbEmployeeMapper.selectJoinPage(queryPage, TbEmployeeDto.class, mpjLambdaWrapper);
        return ConvertUtil.convertPage(page, TbEmployeeVo.class);
    }

    @Override
    public List<TbEmployeeVo> queryAll(TbEmployeeQueryParam query){
        return ConvertUtil.convertList(tbEmployeeMapper.selectList(QueryHelpMybatisPlus.getPredicate(query)), TbEmployeeVo.class);
    }

    @Override
    public TbEmployee getById(Long id) {
        return tbEmployeeMapper.selectById(id);
    }

    @Override
    // @Cacheable(key = "'id:' + #p0")
    public TbEmployeeDto findById(Long id) {
        return ConvertUtil.convert(getById(id), TbEmployeeDto.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(TbEmployeeDto resources) {
        TbEmployee entity = ConvertUtil.convert(resources, TbEmployee.class);
        return tbEmployeeMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(TbEmployeeDto resources){
        TbEmployee entity = ConvertUtil.convert(resources, TbEmployee.class);
        int ret = tbEmployeeMapper.updateById(entity);
        // delCaches(resources.id);
        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeByIds(Set<Long> ids){
        // delCaches(ids);
        return tbEmployeeMapper.deleteBatchIds(ids);
    }

    @Override
    public Object getJobsSelect() {
        return jobMapper.selectList(null);
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
    public void download(List<TbEmployeeDto> all, HttpServletResponse response) throws IOException {
      List<Map<String, Object>> list = new ArrayList<>();
      for (TbEmployeeDto tbEmployee : all) {
        Map<String,Object> map = new LinkedHashMap<>();
              map.put("姓名", tbEmployee.getName());
              map.put("性别", tbEmployee.getGender());
              map.put("拼音码", tbEmployee.getPyCode());
              map.put("所属部门", tbEmployee.getDeptId());
              map.put("籍贯", tbEmployee.getJg());
              map.put("联系电话", tbEmployee.getPhone());
              map.put("手机号码", tbEmployee.getMobile());
              map.put("家庭住址", tbEmployee.getAddress());
              map.put("职位", tbEmployee.getPosition());
              map.put("职称", tbEmployee.getTitle());
              map.put("生日", tbEmployee.getBirthday());
        list.add(map);
      }
      FileUtil.downloadExcel(list, response);
    }*/
}
