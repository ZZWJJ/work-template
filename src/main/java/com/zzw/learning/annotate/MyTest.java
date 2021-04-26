package com.zzw.learning.annotate;

import java.lang.annotation.*;

/**
 * @author : zzw
 * @Description: 测试注解内容
 * @date : 2021/4/26 16:37
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface MyTest {
    String source()default "";
    String myName()default "";
}
