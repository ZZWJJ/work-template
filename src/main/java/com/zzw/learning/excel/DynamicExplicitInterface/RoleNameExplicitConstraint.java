package com.zzw.learning.excel.DynamicExplicitInterface;

import com.zzw.learning.service.IRoleService;
import com.zzw.learning.utils.SpringContextHolder;

/**
 * @author : zzw
 * @Description: 动态角色资源
 * @date : 2020/4/13 17:26
 **/
public class RoleNameExplicitConstraint implements ExplicitInterface {
    private IRoleService roleService = SpringContextHolder.getBean(IRoleService.class);

    @Override
    public String[] source() {
        return roleService.getRoleNames();
    }
}
