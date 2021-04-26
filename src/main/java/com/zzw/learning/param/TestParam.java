package com.zzw.learning.param;

import com.zzw.learning.annotate.MyTest;
import lombok.Data;

/**
 * @author : zzw
 * @Description: TODO
 * @date : 2021/4/26 16:40
 **/
@Data
public class TestParam {

    @MyTest(source = "java",myName = "zzw")
    private String name;

    @MyTest(source = "c++",myName = "zzw")
    private String source;
}
