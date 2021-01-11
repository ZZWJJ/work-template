package com.zzw.learning.annotate;

import java.lang.annotation.*;

/**
 * @author : zzw
 * @Description: excel下拉选项注解
 * @date : 2020/4/8 9:07
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExplicitConstraint {
    //定义固定下拉内容
    String[]source()default {};
    //定义动态下拉内容，
    Class[]sourceClass()default {};
}

