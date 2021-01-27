package com.zzw.learning.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : shenjue
 * @Description: TODO
 * @date : 2020/8/17 11:05
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisTokenVO {

    private Integer code ;

    private String token ;

    private Boolean valid ;

}
