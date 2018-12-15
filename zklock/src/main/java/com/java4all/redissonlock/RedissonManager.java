package com.java4all.redissonlock;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @author wangzhongxiang
 * @date 2018/11/28 20:04
 */
@Component
public class RedissonManager {

  private static Redisson redisson;
  private static Config config = new Config();

  private static void init(){
    config.useSingleServer()
        .setAddress("116.62.62.26:6379")
        .setPassword("wang917917")
        .setConnectionPoolSize(500)//设置对于master节点的连接池中连接数最大为500
        .setIdleConnectionTimeout(10000)//如果当前连接池里的连接数量超过了最小空闲连接数，而同时有连接空闲时间超过了该数值，那么这些连接将会自动被关闭，并从连接池里去掉。时间单位是毫秒
        .setConnectTimeout(30000)//同任何节点建立连接时的等待超时。时间单位是毫秒。
        .setTimeout(3000)//等待节点回复命令的时间。该时间从命令发送成功时开始计时。
        .setReconnectionTimeout(3000)//当与某个节点的连接断开时，等待与其重新建立连接的时间间隔。时间单位是毫秒。
        .setRetryInterval(3000).//在一条命令发送失败以后，等待重试发送的时间间隔。时间单位是毫秒。默认值：1500
        setRetryAttempts(3);////如果尝试达到 retryAttempts（命令失败重试次数） 仍然不能将命令发送至某个指定的节点时，将抛出错误。如果尝试在此限制之内发送成功，则开始启用 timeout（命令等待超时） 计时。默认值：3

    redisson = (Redisson) Redisson.create(config);
  }

  public static Redisson getRedisson(){
    if(null == redisson){
      synchronized (RedissonManager.class){
        if(null == redisson){
          init();
          return redisson;
        }
      }
    }
    return redisson;
  }

}
