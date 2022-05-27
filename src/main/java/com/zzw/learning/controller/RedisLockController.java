package com.zzw.learning.controller;

import com.zzw.learning.service.RedisLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @projectName:work-template
 * @see:com.zzw.learning.redisLock.controller.service
 * @author:zzw
 * @createTime:2022/5/27 15:08
 * @version:1.0
 */
@RestController
@RequestMapping("/redis")
public class RedisLockController {

    @Autowired
    private RedisLockService redisLockService;


    @GetMapping("send")
    public void sendMsg(){
        redisLockService.sendMsg();
    }



}
