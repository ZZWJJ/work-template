package com.zzw.learning.model;

import lombok.Data;

/**
 * @author : zzw
 * @Description: TODO
 * @date : 2021/1/12 16:58
 **/
@Data
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
}
