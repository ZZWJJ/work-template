package com.zzw.learning;

import com.zzw.learning.service.IOrderFormService;
import com.zzw.learning.service.RedisLockService;
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
}
