package com.zzw.learning.service;

import com.zzw.learning.entity.AppKey;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 模块详情表 服务类
 * </p>
 *
 * @author zzw
 * @since 2021-01-27
 */
public interface IAppKeyService extends IService<AppKey> {
    AppKey getExpiredAndSecret(String appKey);

    String getAppKey(Long userId);
}
