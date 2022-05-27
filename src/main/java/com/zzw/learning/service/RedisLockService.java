package com.zzw.learning.service;

import com.zzw.learning.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @projectName:work-template
 * @see:com.zzw.learning.redisLock.service
 * @author:zzw
 * @createTime:2022/5/27 15:23
 * @version:1.0
 */
@Service
@Slf4j
public class RedisLockService {

    @Autowired
    private RedisUtil redisUtil;

    public void sendMsg(){
        String key = "redisLock";
        String value = UUID.randomUUID().toString();
        log.info("进入方法");
        try {
            boolean flag = redisUtil.setIfAbsent(key,value,30, TimeUnit.SECONDS);
            if (flag){
                Thread currentThread = Thread.currentThread();
                log.info("已获取锁");
                currentThread.sleep(200);
                log.info("发送消息");
            }else {
                log.info("未获取锁");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boolean unlockFlag = redisUtil.unLockIfAbsent(key,value);
            log.info("解锁结果：" + unlockFlag);
        }
    }

}
