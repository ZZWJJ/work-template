package com.zzw.learning.support.validate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author : qun.zheng
 * @Description: 警告信息
 * @date : 2019/12/17 18:03
 **/
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class WarnInfo {

    /**
     *  行号
     **/
    private int rowNo;

    /**
     *  列名
     **/
    private String colName;

    /**
     *  提示信息
     **/
    private String errorMessage;
}
