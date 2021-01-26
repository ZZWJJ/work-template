package com.zzw.learning.model;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : zzw
 * @Description: 白名单
 * @date : 2021/1/12 17:09
 **/
@ConfigurationProperties(
        prefix = "whitelist"
)
public class WhiteListConfig {
    private List<String> whiteList = new ArrayList();

    public WhiteListConfig() {
    }

    public List<String> getWhiteList() {
        return this.whiteList;
    }

    public void setLimitSizeList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
