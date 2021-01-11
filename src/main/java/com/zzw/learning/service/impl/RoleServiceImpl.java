package com.zzw.learning.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.learning.entity.Role;
import com.zzw.learning.mapper.RoleMapper;
import com.zzw.learning.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 角色服务实现类
 *
 * @author zzw
 * @since 2020-03-31
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper,Role> implements IRoleService {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public String[] getRoleNames() {
        List<Role> roles = roleMapper.selectList(null);
        List<String> names = roles.stream().map(Role::getName).collect(Collectors.toList());
        String[] res = names.toArray(new String[0]);
        return res;
    }

}
