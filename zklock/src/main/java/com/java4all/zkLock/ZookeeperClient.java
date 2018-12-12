package com.java4all.zkLock;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * description:
 *
 * @author wangzx
 * @version v1.0
 * @date 2018/11/25 17:56
 */
public class ZookeeperClient {

  private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperClient.class);
  private static volatile ZookeeperClient zookeeperClient;
  private final String host = "116.62.62.26";
  private static CountDownLatch countDownLatch = new CountDownLatch(1);


  public static synchronized ZookeeperClient getIntance(){
    if (null == zookeeperClient){
      synchronized (ZookeeperClient.class){
        if(null == zookeeperClient){
          zookeeperClient = new ZookeeperClient();
        }
      }
    }
    return zookeeperClient;
  }

  public  ZooKeeper getClient(){
    try {
      ZooKeeper zooKeeper = new ZooKeeper(host, 3000, new ZkWatcher());
      countDownLatch.await();
      return zooKeeper;
    }catch (IOException ex){
      ex.printStackTrace();
      return null;
    }catch (InterruptedException ex){
      ex.printStackTrace();
      return null;
    }
  }

  private static class ZkWatcher implements Watcher{
    @Override
    public void process(WatchedEvent watchedEvent) {
      if(KeeperState.SyncConnected == watchedEvent.getState()){
        countDownLatch.countDown();
        LOGGER.info("【Zookeeper】zookeeper 连接成功......");
      }else {
        LOGGER.info("【Zookeeper】正在连接中......");
      }
    }
  }

}
