package com.zzw.learning.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author zzw
 * @since 2021-01-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 1 男 0 女
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;

    /**
     * 头像
     */
    @TableField("icon")
    private String icon;

    /**
     * 手机号
     */
    @TableField("phone")
    private String phone;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 0前台用户 1后台用户
     */
    @TableField("sign")
    private Integer sign;

    /**
     * 0 正常 1 弃用
     */
    @TableField("del_flag")
    @TableLogic
    private Integer delFlag;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by", fill = FieldFill.UPDATE)
    private Long updateBy;

    /**
     * 系统模块标识
     */
    @TableField("app_code")
    private String appCode;

    /**
     * 状态 1:启用，2:禁用, 3:注销
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否是管理员
     */
    @TableField("is_admin")
    private Integer isAdmin;

    /**
     * 邮箱
     */
    @TableField("email")
    private String email;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
