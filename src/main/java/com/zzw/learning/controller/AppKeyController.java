package com.zzw.learning.controller;


import com.zzw.learning.entity.AppKey;
import com.zzw.learning.response.DCResponse;
import com.zzw.learning.service.IAppKeyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 模块详情表 前端控制器
 * </p>
 *
 * @author zzw
 * @since 2021-01-27
 */
@RestController
@RequestMapping("/{version}/app-key")
@Api(value = "/{version}/app-key", tags = {"模块详情接口"})
public class AppKeyController {

    @Autowired
    private IAppKeyService appKeyService;

    @ApiOperation(value = "获取appkey信息", notes = "获取appkey信息")
    @PostMapping(value = "/getExpiredAndSecret")
    public DCResponse<AppKey> getExpiredAndSecret(@RequestParam("appKeyCode") String appKeyCode) {
        return DCResponse.success(appKeyService.getExpiredAndSecret(appKeyCode));
    }



}

