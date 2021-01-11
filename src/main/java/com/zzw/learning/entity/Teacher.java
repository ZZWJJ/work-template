package com.zzw.learning.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 教师信息管理
 * </p>
 *
 * @author zzw
 * @since 2020-03-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel
public class Teacher extends Model<Teacher> {

    private static final long serialVersionUID = 1L;

    /**
     * 教师id
     */
    @TableId("id")
    @ApiModelProperty("教师id")
    private String id;

    /**
     * 账户id
     */
    @TableId("user_id")
    @ApiModelProperty("账户id")
    private String userId;

    /**
     * 工号
     */
    @TableField("job_number")
    @ApiModelProperty("工号")
    private String jobNumber;

    /**
     * 名字
     */
    @TableField("name")
    @ApiModelProperty("名字")
    private String name;

    /**
     * 性别（M：男 F：女）
     */
    @TableField("sex")
    @ApiModelProperty("性别（M：男 F：女）")
    private String sex;

    /**
     * 头像
     */
    @TableField("avatar")
    @ApiModelProperty("头像")
    private String avatar;

    /**
     * 生日
     */
    @TableField("birthday")
    @ApiModelProperty("生日")
    private LocalDateTime birthday;

    /**
     * 民族
     */
    @TableField("nationality")
    @ApiModelProperty("民族")
    private String nationality;

    /**
     * 籍贯
     */
    @TableField("native_place")
    @ApiModelProperty("籍贯")
    private String nativePlace;

    /**
     * 政治面貌
     */
    @TableField("political")
    @ApiModelProperty("政治面貌")
    private String political;

    /**
     * 婚姻状况
     */
    @TableField("marriage_status")
    @ApiModelProperty("婚姻状况:0 未婚 ，1：已婚")
    private Integer marriageStatus;

    /**
     * 学历
     */
    @TableField("education")
    @ApiModelProperty("学历")
    private String education;

    /**
     * 毕业学校
     */
    @TableField("graduate_school")
    @ApiModelProperty("毕业学校")
    private String graduateSchool;

    /**
     * 专业
     */
    @TableField("major")
    @ApiModelProperty("专业")
    private String major;

    /**
     * 证件类型
     */
    @TableField("certificate_type")
    @ApiModelProperty("证件类型")
    private String certificateType;

    /**
     * 证件号码
     */
    @TableField("certificate_num")
    @ApiModelProperty("证件号码")
    private String certificateNum;

    /**
     * 电话
     */
    @TableField("phone")
    @ApiModelProperty("电话")
    private String phone;

    /**
     * 电子邮件
     */
    @TableField("email")
    @ApiModelProperty("电子邮件")
    private String email;

    /**
     * 家庭地址
     */
    @TableField("address")
    @ApiModelProperty("家庭地址")
    private String address;

    /**
     * 部门
     */
    @TableField("dept_id")
    @ApiModelProperty("部门")
    private String deptId;

    /**
     * 职位
     */
    @TableField("job")
    @ApiModelProperty("职位")
    private String job;

    /**
     * 系统角色id
     */
    @TableField("role_id")
    @ApiModelProperty("系统角色id")
    private String roleId;

    /**
     * 职称
     */
    @TableField("title")
    @ApiModelProperty("职称")
    private String title;

    /**
     * 员工类型
     */
    @TableField("employee_type")
    @ApiModelProperty("员工类型 1：编制 2：合同 3：临时 4：外聘")
    private Integer employeeType;

    /**
     * 加入工作时间
     */
    @TableField("join_job_time")
    @ApiModelProperty("加入工作时间")
    private LocalDateTime joinJobTime;

    /**
     * 加入本校时间
     */
    @TableField("join_campus_time")
    @ApiModelProperty("加入本校时间")
    private LocalDateTime joinCampusTime;

    /**
     * 在职状态;1:在职，2:离职，3：进修，4：退休
     */
    @TableField("job_status")
    @ApiModelProperty("在职状态;1:在职，2:离职，3：进修，4：退休")
    private Integer jobStatus;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    /**
     * 删除标识 0：正常 1：已删除
     */
    @TableField("del_flag")
    @TableLogic
    @ApiModelProperty("删除标识 0：正常 1：已删除")
    private int delFlag;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
