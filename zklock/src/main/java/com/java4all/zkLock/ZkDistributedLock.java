package com.java4all.zkLock;

import com.java4all.zkLock.ZookeeperClient;
import java.util.concurrent.TimeUnit;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description:
 *
 * @author wangzx
 * @version v1.0
 * @date 2018/11/26 18:38
 */
public class ZkDistributedLock {
  private static  final Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);
  private static final String LOCK_PREFIX = "/lock-";
  private static  ZooKeeper zookeeper = null;

  static {
    ZookeeperClient intance = ZookeeperClient.getIntance();
    zookeeper = intance.getClient();
  }

  public static void acquire(String key,long time, TimeUnit unit) throws Exception {

    int retryCount = 0;

    try {
      zookeeper.create(LOCK_PREFIX+key,"".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
      logger.info("【Zookeeper Lock】success to acquire lock for :"+LOCK_PREFIX+key);
    }catch (Exception ex) {
      while (true) {
        try {
          Thread.sleep(20);
          zookeeper.create(LOCK_PREFIX+ key, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        } catch (Exception ex1) {
          ex1.printStackTrace();
          logger.info("【Zookeeper Lock】retry to acquire lock for :"+LOCK_PREFIX+key);
          retryCount++;
          continue;
        }
        ex.printStackTrace();
        logger.info("【Zookeeper Lock】success to acquire lock for :["+"] after "+retryCount+"times try......");
        break;
      }
    }
  }


  public static void release(String key) throws Exception {
    try {
      zookeeper.delete(LOCK_PREFIX+key,-1);
      logger.info("【Zookeeper Lock】success to delete lock for :"+LOCK_PREFIX+key);
    }catch (Exception ex){
      logger.info("【Zookeeper Lock】faield to delete lock for :"+LOCK_PREFIX+key);
    }
  }
}
