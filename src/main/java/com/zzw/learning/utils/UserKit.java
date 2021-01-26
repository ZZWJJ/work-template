package com.zzw.learning.utils;

import com.zzw.learning.model.UserInfo;

import java.util.Objects;

/**
 * @author : zzw
 * @Description: 登录用户
 * @date : 2021/1/12 16:56
 **/
public class UserKit {
    public static final ThreadLocal<UserInfo> t1 = new ThreadLocal();

    public UserKit() {
    }

    public static void init() {
        UserInfo userInfo = buildDummyUser();
        t1.set(userInfo);
    }

    public static UserInfo getLoginUser() {
        return (UserInfo)t1.get();
    }

    public static void remove() {
        t1.remove();
    }

    public static void set(UserInfo userInfo) {
        t1.set(userInfo);
    }

    public static UserInfo buildDummyUser() {
        UserInfo userInfo = new UserInfo();
        userInfo.setId("dummy");
        userInfo.setUserName("dummy");
        userInfo.setRealName("游客");
        return userInfo;
    }

    public static String getLoginUserId() {
        UserInfo userInfo = getLoginUser();
        return Objects.nonNull(userInfo) ? String.valueOf(userInfo.getId()) : null;
    }
}
