package com.zzw.learning.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户登录返回信息")
public class LoginUserVO implements Serializable {

    @ApiModelProperty(value = "用户信息")
    private UserSimpleVO userSimpleVO;


    @ApiModelProperty(value = "token")
    private String token;

    /**
     * 是否要临近过期
     */
    private Boolean isNearExpired = false ;
}
