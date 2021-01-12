package com.zzw.learning.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 验证码实体
 */
@Data
public class VifyCodeDTO {
    @ApiModelProperty("uuid")
    private String uuid;

    @ApiModelProperty("base编码")
    private String base64Code;
}
