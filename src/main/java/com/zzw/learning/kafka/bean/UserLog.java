package com.zzw.learning.kafka.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author : zzw
 * @Description: 测试bean
 * @date : 2021/1/28 16:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLog {
    private String userName;
    private String userId;
    private String state;
}
