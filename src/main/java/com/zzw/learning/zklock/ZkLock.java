package com.zzw.learning.zklock;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @projectName:work-template
 * @see:com.zzw.learning.zklock
 * @author:zzw
 * @createTime:2022/5/31 15:12
 * @version:1.0
 */
@Slf4j
public class ZkLock implements AutoCloseable, Watcher {

    private ZooKeeper zooKeeper;

    private String znode;

    public ZkLock() throws IOException {
        this.zooKeeper = new ZooKeeper("localhost:2181", 10000, this);
    }

    public Boolean getLock(String businessCode) throws KeeperException, InterruptedException {

        Stat stat = zooKeeper.exists("/" + businessCode, false);
        if (stat == null) {
            zooKeeper.create("/" + businessCode,
                    businessCode.getBytes(),
                    ZooDefs.Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        }

        // 创建瞬时有序节点 类似：/order/order_000001
        znode = zooKeeper.create("/" + businessCode + "/" + businessCode + "_",
                businessCode.getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE,
                CreateMode.EPHEMERAL_SEQUENTIAL);
        log.info("创建了瞬时节点：" + znode);
        // 获取当前zk目录下的所有子节点
        List<String> childrenNodes = zooKeeper.getChildren("/" + businessCode, false);
        if (CollectionUtil.isNotEmpty(childrenNodes)) {
            // 排序
            Collections.sort(childrenNodes);
            // 获取第一个子节点
            String firstNode = childrenNodes.get(0);
            // 如果都一个子节点为刚刚创建的节点，则获取锁
            if (znode.endsWith(firstNode)) {
                return true;
            }

            // 如果不是第一个子节点，则创建监听器，监听前一个节点
            String lastNode = firstNode;
            for (String node : childrenNodes) {
                if (znode.endsWith(node)) {
                    zooKeeper.exists("/" + businessCode + "/" + lastNode, true);
                    break;
                } else {
                    lastNode = node;
                }
            }
            synchronized (this) {
                wait();
            }
        }
        return false;
    }


    @Override
    public void close() throws Exception {
        zooKeeper.delete(znode, -1);
        zooKeeper.close();
        log.info("我释放了锁:" + znode);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Event.EventType.NodeDeleted) {
            synchronized (this) {
                notify();
            }
        }
    }
}
