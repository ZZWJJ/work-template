package com.zzw.learning.mapper;

import com.zzw.learning.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author zzw
 * @since 2021-01-27
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

}
