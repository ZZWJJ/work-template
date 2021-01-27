package com.zzw.learning.service.impl;

import com.zzw.learning.entity.AppKey;
import com.zzw.learning.entity.User;
import com.zzw.learning.mapper.AppKeyMapper;
import com.zzw.learning.mapper.UserMapper;
import com.zzw.learning.service.IAppKeyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.learning.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * <p>
 * 模块详情表 服务实现类
 * </p>
 *
 * @author zzw
 * @since 2021-01-27
 */
@Service
public class AppKeyServiceImpl extends ServiceImpl<AppKeyMapper, AppKey> implements IAppKeyService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IAppKeyService appKeyService;

    @Override
    public AppKey getExpiredAndSecret(String appKeyCode) {
        AppKey appKey = lambdaQuery().select().eq(AppKey::getAppCode, appKeyCode).one();
        String jwtSecret = Optional.ofNullable(appKey).map(AppKey::getPrivateKey).orElse(JwtUtils.JWT_SECRET);
        Integer expired = Optional.ofNullable(appKey).map(AppKey::getExpired).orElse(JwtUtils.DEFAULT_EXPIRED_DATE);
        return new AppKey(jwtSecret, expired);
    }

    @Override
    public String getAppKey(Long userId) {
        User user = userMapper.selectById(userId);
        if (user != null) {
            return user.getAppCode();
        } else {
            return null;
        }
    }
}
