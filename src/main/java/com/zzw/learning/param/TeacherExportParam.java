package com.zzw.learning.param;

import com.alibaba.excel.annotation.ExcelProperty;
import com.zzw.learning.annotate.ExplicitConstraint;
import com.zzw.learning.excel.DynamicExplicitInterface.RoleNameExplicitConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : zzw
 * @Description: TODO
 * @date : 2020/4/7 15:00
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherExportParam {
    /**
     * 工号
     */
    @ExcelProperty(value = "*工号", index = 0)
    private String jobNumber;

    /**
     * 名字
     */
    @ExcelProperty(value = "*姓名", index = 1)
    private String name;

    /**
     * 性别（M：男 F：女）
     */
    @ExcelProperty(value = "*性别", index = 2)
    @ExplicitConstraint(source = {"男","女"})
    private String sex;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像", index = 3)
    private String avatar;

    /**
     * 生日
     */
    @ExcelProperty(value = "生日", index = 4)
    private String birthday;

    /**
     * 民族
     */
    @ExcelProperty(value = "民族", index = 5)
    @ExplicitConstraint(source = {"汉"})
    private String nationality;

    /**
     * 籍贯
     */
    @ExcelProperty(value = "*籍贯", index = 6)
    private String nativePlace;

    /**
     * 政治面貌
     */
    @ExcelProperty(value = "政治面貌", index = 7)
    @ExplicitConstraint(source = "党员")
    private String political;

    /**
     * 婚姻状况0 未婚 ，1：已婚
     */
    @ExcelProperty(value = "*婚姻状况", index = 8)
    @ExplicitConstraint(source = {"未婚","已婚"})
    private String marriageStatus;

    /**
     * 学历
     */
    @ExcelProperty(value = "学历", index = 9)
    @ExplicitConstraint(source = {"本科"})
    private String education;

    /**
     * 毕业学校
     */
    @ExcelProperty(value = "毕业学校", index = 10)
    private String graduateSchool;

    /**
     * 专业
     */
    @ExcelProperty(value = "专业", index = 11)
    private String major;

    /**
     * 证件类型
     */
    @ExcelProperty(value = "证件类型", index = 12)
    @ExplicitConstraint(source = {"身份证"})
    private String certificateType;

    /**
     * 证件号码
     */
    @ExcelProperty(value = "证件号码", index = 13)
    private String certificateNum;

    /**
     * 电话
     */
    @ExcelProperty(value = "*电话", index = 14)
    private String phone;

    /**
     * 电子邮件
     */
    @ExcelProperty(value = "电子邮件", index = 15)
    private String email;

    /**
     * 家庭地址
     */
    @ExcelProperty(value = "家庭地址", index = 16)
    private String address;

    /**
     * 部门
     */
    @ExcelProperty(value = "*部门", index = 17)
    @ExplicitConstraint(source = {"开发部"})
    private String deptId;

    /**
     * 职位id
     */
    @ExplicitConstraint(source = {"软件开发"})
    @ExcelProperty(value = "*职位", index = 18)
    private String job;

    /**
     * 职称
     */
    @ExcelProperty(value = "职称", index = 19)
    @ExplicitConstraint(source = {"软件开发工程师"})
    private String title;

    /**
     * 员工类型 1：编制 2：合同 3：临时 4：外聘
     */
    @ExcelProperty(value = "员工类型", index = 20)
    @ExplicitConstraint(source = {"编制","合同","临时","外聘"})
    private String employeeType;

    /**
     * 加入工作时间
     */
    @ExcelProperty(value = "加入工作时间", index = 21)
    private String joinJobTime;

    /**
     * 加入本校时间
     */
    @ExcelProperty(value = "加入本校时间", index = 22)
    private String joinCampusTime;

    /**
     * 在职状态;1:在职，2:离职，3：进修，4：退休
     */
    @ExcelProperty(value = "*在职状态", index = 23)
    @ExplicitConstraint(source = {"在职","离职","进修","退休"})
    private String jobStatus;

    /**
     * 系统角色
     */
    @ExcelProperty(value = "*系统角色", index = 24)
    @ExplicitConstraint(sourceClass = RoleNameExplicitConstraint.class)
    private String role;
}
