package com.zzw.learning.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzw.learning.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * <p>
 *  角色Mapper 接口
 * </p>
 *
 * @author chenzhang
 * @since 2020-03-31
 */
@Mapper
@Repository
public interface RoleMapper extends BaseMapper<Role> {

}
