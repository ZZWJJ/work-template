package com.zzw.learning.Interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.zzw.learning.exception.ServiceException;
import com.zzw.learning.model.UserInfo;
import com.zzw.learning.model.WhiteListConfig;
import com.zzw.learning.service.IUserService;
import com.zzw.learning.utils.UserKit;
import com.zzw.learning.vo.AnalysisTokenVO;
import com.zzw.learning.vo.LoginUserVO;
import io.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author : zzw
 * @Description: 登录拦截器
 * @date : 2021/1/12 16:55
 **/
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
    @Autowired
    private IUserService authApi;
    @Autowired
    private WhiteListConfig whiteListConfig;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    List<String> authUrlList = new ArrayList();

    public LoginInterceptor(WhiteListConfig whiteListConfig) {
        this.whiteListConfig = whiteListConfig;
        this.authUrlList = whiteListConfig.getWhiteList();
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String servletPath = request.getServletPath();
        Iterator var5 = this.authUrlList.iterator();

        boolean flag;
        do {
            String temp;
            if (!var5.hasNext()) {
                String token = request.getHeader("Authorization");
                temp = null;

                if (StringUtil.isNullOrEmpty(token)){
                    throw new ServiceException(505,"token为空！");
                }

                try {
                    AnalysisTokenVO analysisTokenVO = this.authApi.analysisToken(token,request);
                    if (analysisTokenVO.getValid()) {
                        LoginUserVO loginUserVO = this.authApi.getUserInfo(request);
                        UserInfo userInfo = (UserInfo) BeanUtil.toBean(loginUserVO.getUserSimpleVO(), UserInfo.class);
                        UserKit.set(userInfo);
                    }

                    return analysisTokenVO.getValid();
                } catch (Exception var9) {
                    throw var9;
                }
            }

            temp = (String)var5.next();
            flag = this.antPathMatcher.match(temp, servletPath);
        } while(!flag);

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserKit.remove();
    }



}
