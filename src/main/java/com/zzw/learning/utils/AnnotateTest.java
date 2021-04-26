package com.zzw.learning.utils;


import com.zzw.learning.annotate.MyTest;

import java.lang.reflect.Field;
import java.util.stream.Stream;

/**
 * @author : zzw
 * @Description: TODO
 * @date : 2021/4/26 16:42
 **/
public class AnnotateTest {

    public static void getTest(Class<?> clzz){

        Field[] fields = clzz.getDeclaredFields();
        Stream.of(fields).forEach(field->{
            MyTest myTest = field.getAnnotation(MyTest.class);
            System.out.println(myTest.myName()+myTest.source());
        });
    }
}
