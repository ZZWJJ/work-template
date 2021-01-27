package com.zzw.learning.Interceptor;

import com.zzw.learning.model.UserInfo;
import com.zzw.learning.model.WhiteListConfig;
import com.zzw.learning.utils.UserKit;
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
    //@Autowired
    //private UserApi authApi;
    @Autowired
    private WhiteListConfig whiteListConfig;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();
    List<String> authUrlList = new ArrayList();

    public LoginInterceptor(WhiteListConfig whiteListConfig) {
        this.whiteListConfig = whiteListConfig;
        this.authUrlList = whiteListConfig.getWhiteList();
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        String servletPath = request.getServletPath();
        Iterator var5 = this.authUrlList.iterator();
        if (!StringUtils.hasText(token)) {
            UserKit.init();
            return true;
        } else {
            boolean flag;
            do {
                if (!var5.hasNext()) {
                    //UserInfo userInfo = this.authApi.getUserByToken(token);
                    //UserKit.set(userInfo);
                    return true;
                }

                String temp = (String)var5.next();
                flag = this.antPathMatcher.match(temp, servletPath);
            } while(!flag);

            return true;
        }
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserKit.remove();
    }



}
