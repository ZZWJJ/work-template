package com.zzw.learning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.learning.entity.Role;


/**
 * 角色服务类
 *
 * @author chenzhang
 * @since 2020-03-31
 */
public interface IRoleService extends IService<Role> {
    /**
     * @Author zzw
     * @Description 查询所有角色的名称
     **/
    String[] getRoleNames();

}
