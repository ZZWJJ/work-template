package com.zzw.learning.service;

import com.zzw.learning.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zzw.learning.model.UserInfo;
import com.zzw.learning.vo.AnalysisTokenVO;
import com.zzw.learning.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author zzw
 * @since 2021-01-27
 */
public interface IUserService extends IService<User> {

    AnalysisTokenVO analysisToken(String token, HttpServletRequest request);

    LoginUserVO getUserInfo(HttpServletRequest request);

}
