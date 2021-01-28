package com.zzw.learning.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.zzw.learning.constant.ConstantStore;
import com.zzw.learning.entity.AppKey;
import com.zzw.learning.entity.User;
import com.zzw.learning.mapper.UserMapper;
import com.zzw.learning.model.UserInfo;
import com.zzw.learning.service.IAppKeyService;
import com.zzw.learning.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzw.learning.utils.JwtTokenUtil;
import com.zzw.learning.utils.JwtUtils;
import com.zzw.learning.vo.AnalysisTokenVO;
import com.zzw.learning.vo.LoginUserVO;
import com.zzw.learning.vo.UserSimpleVO;
import io.jsonwebtoken.Claims;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

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

    @Autowired
    private IUserService userService;

    @Autowired
    private IAppKeyService appKeyService;

    @Override
    public AnalysisTokenVO analysisToken(String token, HttpServletRequest request) {
        String appCode = request.getHeader(ConstantStore.HEADER_APP_KEY);
        AppKey appKey = appKeyService.getExpiredAndSecret(appCode);
        return AnalysisTokenVO.builder().valid(JwtUtils.checkToken(token, appKey.getPrivateKey())).build();
    }

    @Override
    public LoginUserVO getUserInfo(HttpServletRequest request) {
        String appCode = request.getHeader(ConstantStore.HEADER_APP_KEY);
        String token = request.getHeader(ConstantStore.HEADER_AUTHORIZATION);
        AppKey appKey = appKeyService.getExpiredAndSecret(appCode);
        Date date = JwtUtils.getExpirationDateFromToken(token, appKey.getPrivateKey());
        Claims claims = JwtUtils.getClaimFromToken(token, appKey.getPrivateKey());
        User user = userService.getById(claims.getSubject()) ;
//        UserSimpleVO userSimpleVO = UserSimpleVO.builder().id(Long.parseLong(claims.get(JwtConstans.JWT_KEY_ID).toString())).username(claims.get(JwtConstans.JWT_KEY_USER_NAME).toString()).phone(claims.get(JwtConstans.JWT_KEY_PHONE).toString()).build();
        UserSimpleVO userSimpleVO = BeanUtil.toBean(user, UserSimpleVO.class) ;
        LoginUserVO loginUserVO = LoginUserVO.builder().userSimpleVO(userSimpleVO).build();
        if (DateUtil.isExpired(new Date(), DateField.MINUTE, JwtUtils.NEAR_EXPIRED_DATE, date)) {
            loginUserVO.setIsNearExpired(true);
            loginUserVO.setToken(JwtUtils.generateToken(BeanUtil.toBean(userSimpleVO, UserInfo.class), appKey.getPrivateKey(), appKey.getExpired()));
        } else {
            loginUserVO.setIsNearExpired(false);
        }
        return loginUserVO;
    }
}
