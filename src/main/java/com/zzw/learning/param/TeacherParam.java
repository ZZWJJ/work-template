package com.zzw.learning.param;

import lombok.Data;

/**
 * @author : zzw
 * @Description: 教职工导入
 * @date : 2020/4/7 14:36
 **/
@Data
public class TeacherParam {

    /**
     * 工号
     */
    private String jobNumber;

    /**
     * 名字
     */
    private String name;

    /**
     * 性别（M：男 F：女）
     */
    private String sex;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 民族
     */
    private String nationality;

    /**
     * 籍贯
     */
    private String nativePlace;

    /**
     * 政治面貌
     */
    private String political;

    /**
     * 婚姻状况
     */
    private String marriageStatus;

    /**
     * 学历
     */
    private String education;

    /**
     * 毕业学校
     */
    private String graduateSchool;

    /**
     * 专业
     */
    private String major;

    /**
     * 证件类型
     */
    private String certificateType;

    /**
     * 证件号码
     */
    private String certificateNum;

    /**
     * 电话
     */
    private String phone;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 家庭地址
     */
    private String address;

    /**
     * 部门
     */
    private String deptId;

    /**
     * 职位
     */
    private String job;

    /**
     * 职称
     */
    private String title;

    /**
     * 员工类型
     */
    private String employeeType;

    /**
     * 加入工作时间
     */
    private String joinJobTime;

    /**
     * 加入本校时间
     */
    private String joinCampusTime;

    /**
     * 在职状态;1:在职，2:离职，3：进修，4：退休
     */
    private String jobStatus;

    /**
     * 系统角色
     */
    private String role;
}
