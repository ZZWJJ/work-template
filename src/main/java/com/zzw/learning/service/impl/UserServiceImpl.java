package com.zzw.learning.service.impl;

import com.zzw.learning.entity.User;
import com.zzw.learning.mapper.UserMapper;
import com.zzw.learning.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author zzw
 * @since 2021-01-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
