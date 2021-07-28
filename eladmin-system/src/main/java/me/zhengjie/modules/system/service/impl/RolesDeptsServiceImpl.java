package me.zhengjie.modules.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.AllArgsConstructor;
import me.zhengjie.base.impl.CommonServiceImpl;
import me.zhengjie.modules.system.domain.RolesDepts;
import me.zhengjie.modules.system.service.RolesDeptsService;
import me.zhengjie.modules.system.service.mapper.RolesDeptsMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jinjin on 2020-09-25.
 */
@AllArgsConstructor
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RolesDeptsServiceImpl extends CommonServiceImpl<RolesDeptsMapper, RolesDepts> implements RolesDeptsService {
    private final RolesDeptsMapper rolesDeptsMapper;

    @Override
    public List<Long> queryDeptIdByRoleId(Long id) {
        return lambdaQuery().eq(RolesDepts::getRoleId, id).list().stream().map(RolesDepts::getDeptId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> queryRoleIdByDeptId(Long id) {
        return lambdaQuery().eq(RolesDepts::getDeptId, id).list().stream().map(RolesDepts::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByRoleId(Long id) {
        return lambdaUpdate().eq(RolesDepts::getRoleId, id).remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean removeByDeptId(Long id) {
        return lambdaUpdate().eq(RolesDepts::getDeptId, id).remove();
    }
}
