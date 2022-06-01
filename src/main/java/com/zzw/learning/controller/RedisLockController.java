package com.zzw.learning.controller;

import com.zzw.learning.service.RedisLockService;
import com.zzw.learning.config.CuratorConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @projectName:work-template
 * @see:com.zzw.learning.redisLock.controller.service
 * @author:zzw
 * @createTime:2022/5/27 15:08
 * @version:1.0
 */
@RestController
@RequestMapping("/lock")
@Slf4j
public class RedisLockController {

    @Autowired
    private RedisLockService redisLockService;
    @Autowired
    private CuratorConfig curatorConfig;


    @GetMapping("/redis/send")
    public void sendMsg(){
        redisLockService.sendMsg();
    }

    @GetMapping("/curator/lock")
    public void curatorLock() throws Exception {
        log.info("我进入了方法");
        CuratorFramework client = curatorConfig.getCuratorClient();
        InterProcessMutex lock = new InterProcessMutex(client, "/order");
        try {
            if (lock.acquire(30, TimeUnit.SECONDS)) {
                log.info("我获取了锁");
                Thread.sleep(10000);
            }
        } finally {
            log.info("我释放了锁");
            lock.release();
        }
        log.info("方法执行完成");
    }

}
