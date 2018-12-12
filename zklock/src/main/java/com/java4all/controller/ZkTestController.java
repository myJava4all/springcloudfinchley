package com.java4all.controller;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * description:
 *
 * @author wangzx
 * @version v1.0
 * @date 2018/11/24 23:14
 */
public class ZkTestController {

  public static void main(String[]args)throws Exception{
    ZooKeeper zk = new ZooKeeper("116.62.62.26", 3000, new Watcher() {
      @Override
      public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent.toString());
      }
    });
    System.out.println("OK");

    //创建一个节点
    //zk.create("/companyid","1qaz".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    //创建一个二级节点
//    zk.create("/companyid/addressid","123".getBytes(),Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
//    zk.create("/companyid/taxid","5555".getBytes(),Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);

    //获取节点数据
    byte[] addressid = zk.getData("/companyid/addressid", false, null);
    byte[] taxid = zk.getData("/companyid/taxid", false, null);
    System.out.println(new String(addressid));
    System.out.println(new String(taxid));

    //修改节点数据
    zk.setData("/companyid/addressid","666addressid".getBytes(),-1);
    byte[] addressidnew = zk.getData("/companyid/addressid", false, null);
    System.out.println(new String(addressidnew));

    //删除节点数据
    zk.delete("/companyid/taxid",-1);
    System.out.println(new String(taxid));

    zk.close();

  }


  public String getDistributeLock(String var){
return null;
  }
}
