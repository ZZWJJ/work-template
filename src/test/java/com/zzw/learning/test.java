package com.zzw.learning;

import com.zzw.learning.service.IOrderFormService;
import com.zzw.learning.service.RedisLockService;
import com.zzw.learning.zklock.ZkLock;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.*;

/**
 * @description:
 * @projectName:work-template
 * @see:PACKAGE_NAME
 * @author:zzw
 * @createTime:2022/5/26 15:55
 * @version:1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@ComponentScan("com.zzw.learning")
@Slf4j
public class test {
    @Autowired
    private IOrderFormService orderService;
    @Autowired
    private RedisLockService redisLockService;

    @Test
    public void test() throws Exception {
        CountDownLatch cdl = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                try {
                    cyclicBarrier.await();
                    orderService.order("1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        cdl.await();
        es.shutdown();
    }

    @Test
    public void testRedisLock() throws Exception {
        CountDownLatch cdl = new CountDownLatch(5);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5);

        ExecutorService es = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                try {
                    cyclicBarrier.await();
                    redisLockService.sendMsg();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        cdl.await();
        es.shutdown();
    }

    @Test
    public void testZkLock() throws Exception {
        for (int i = 0; i < 5; i++) {
            log.info("我进入了方法");
            // 设置了自动关闭AutoCloseable，若不放在try的（）内，则不会自动关闭
            try (ZkLock zkLock = new ZkLock()) {
                if (zkLock.getLock("order")) {
                    log.info("我获取了锁");
                    Thread.sleep(10000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testCuratorLock() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient("localhost:2181", retryPolicy);
        client.start();

        InterProcessMutex lock = new InterProcessMutex(client, "/order");
        if (lock.acquire(10, TimeUnit.SECONDS)) {
            try {
                // do some work inside of the critical section here
            } finally {
                lock.release();
            }
        }
        client.close();

    }
}
