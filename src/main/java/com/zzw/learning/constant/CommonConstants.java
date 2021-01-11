package com.zzw.learning.constant;

import java.util.HashSet;
import java.util.Set;

/**
 * @author : shenjue
 * @Description: TODO
 * @date : 2019/12/10 15:54
 **/
public class CommonConstants {


    public static final String EXCEL_ERROR_MSG = "第{0}行,{1}这一列，{2}";
    public static final String FILE_NOT_EXISTS = "模板定义中文件这一项不能为空！";


    public static final int DATA_ERROR = 1;
    public static final int EFFECTIVE = 1;
    public static final int REQUEST_CODE = 200;
    public static final Integer REQUEST_ERROR = 400;
    public static final Integer SEMESTER_CHANGE_LIST = 3;
    public static final Integer REGISTER_DONE = 1;
    public static final Integer UN_REGISTER = 0;
    public static final Integer STUDENT_TYPE = 1;
    public static final Integer NO_SCHOOL_ROOL_TYPE = 0;
    public static final String STUDENT_INIT_ROLE_ID = "1275000465390288897";
    public static final Integer DISABLED = 1;
    public static final Integer ENABLED = 0;
    public static final Integer LOGOUT = 2;
    public static final String STUDENT_SCHOOL_CODE = "学籍号不能重复！";
    public static final String STUDENT_NO_CODE = "学号不能重复！";
    public static final String IDENTITY_NO_CODE = "身份证号不能重复！";
    public static final String IDENTITY_NO_CODE_ERROR = "身份证号不合法！";
    public static final String FRESH_DELETE_ERROR = "学生数据中有该新生的引用不能删除！";
    public static final String STUDENT_REGISTER_ERROR = "当前学期已经注册过，不能再进行注册";
    public static final String CHECK_STUDENT_REGISTER = "请选择学生！";
    public static final String STUDENT_STATUS_ERROR = "学籍状态没有改变，请重新修改!";
    public static final String STUDENT_MAJOR_CHANGE_ERROR = "学生换专业不能跟当前专业一致！";
    public static final String STUDENT_CLASS_CHANGE_ERROR = "学生换班级必须跟当前专业一致！";
    public static final String TERM_YEAR = "学年";
    public static final String BEFORE_TREM = "上学期";
    public static final String AFTER_TREM = "下学期";
    public static final Integer NEW_FRESH_MAN = 0;
    public static final Integer STUDENT = 1;
    public static final String SPLIT = "-";
    public static final String FILE_EMPTY = "请选择文件！";
    public static final String ZIP_CONTEXT_TYPE = "application/zip";
    public static final String RAR_CONTEXT_TYPE = "application/x-rar-compressed";
    public static final String DELETE_ERROR_MSG = "资源分类下面还有分类，不能进行删除";
    public static final String DELETE_FIXED_MSG = "该资源分类是系统固定分类，不能进行删除";
    public static final Integer FIRST = 0;
    public static final Integer IMPORT_SOURCE = 1;
    public static final Integer BATCH_SOURCE = 2;
    public static final String BREAK_LINE = "~";
    public static final String COMPOSE_DATE_BREAK_LINE = ";";
    public static final Integer DELETE = 1;
    public static final Integer REMOVE = 0;
    public static final Integer DESC_DATA_ID = 1;
    public static final Integer TEMP_ID = 2;
    public static final String STANDBY_USER = "关键岗位人员不能重复";
    public static final String MAP_HIERARCHY = "-地图层级";
    public static final String FILE_DESC = "-描述";
    public static final String MEMBER_TYPE_EXISTS = "人员分类已经存在！";
    public static final String FIELD_NAME_REPEAT = "字段名称不能重复！";
    public static final String BUSINESS_ID = "业务ID";
    public static final Long MEMBER_TEMPLATE = 1L;
    public static final Integer INCLUDE_MEMBER_INFO = 2;
    public static final Integer ONLY_BUSNIESS_INFO = 1;
    public static final String UPGRADE = "0";
    public static final Integer REPORTED  = 1 ;
    public static final String EMPTY_DICT  = "-100";
    public static final String EMPTY_DICT_NAME  = "无";
    public static final String STUDENT_NOT_EXISTS  = "该用户没有对应的学生信息！";


    /**
     * ZIP压缩文档
     */
    public static final Set<String> ZIP_CONTEXT_TYPES = new HashSet<String>() {
        private static final long serialVersionUID = 1L;

        {
            add("application/zip");
            add("application/x-zip");
            add("application/x-zip-compressed");
        }

        @Override
        public boolean contains(Object o) {
            boolean flag = super.contains(o);
            if (!flag && o.toString().startsWith("application/") && o.toString().toUpperCase().contains("ZIP")) {
                return true;
            }
            return flag;
        }
    };
}
