package com.zzw.learning.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : zzw
 * @Description: 用户信息
 * @date : 2021/1/12 16:58
 **/
@Data
@NoArgsConstructor
public class UserInfo {
    private String id;
    private String userName;
    private String realName;
    private String phone;
    private String email;
    private String icon;
    private Integer status;
    private Integer type;
    private Integer isAdmin;

    public UserInfo(String id, String userName, String phone, String icon, Integer isAdmin) {
        this.id = id;
        this.userName = userName;
        this.phone = phone;
        this.icon = icon;
        this.isAdmin = isAdmin;
    }
}
