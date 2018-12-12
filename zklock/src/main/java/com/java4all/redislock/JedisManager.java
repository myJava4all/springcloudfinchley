package com.java4all.redislock;

import com.java4all.zkLock.ZookeeperClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * description:
 * 管理jedisPool
 * @author wangzhongxiang
 * @date 2018/12/4 14:52
 */
public class JedisManager {

  private static final Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);
  private static final String REDIS_HOST = "116.62.62.26";
  private static final Integer REDIS_PORT = 6379;
  private static JedisPool jedisPool;
  private static JedisPoolConfig config = new JedisPoolConfig();

  private static void init(){
    config.setMaxTotal(1000);
    config.setMaxIdle(10);
    config.setMaxWaitMillis(10*1000);
    config.setTestOnBorrow(true);//borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    config.setTestOnReturn(true);//return一个jedis实例给pool时，是否检查连接可用,设置为true时，返回的对象如果验证失败，将会被销毁，否则返回
  }

  public static JedisPool getJedisPool(){
    if(null == jedisPool){
      synchronized (JedisPool.class){
        if(null == jedisPool){
          init();
          jedisPool = new JedisPool(config, REDIS_HOST, REDIS_PORT, 3000,"wang917917");
          logger.info("【Redis lock】jedisPool初始化成功......");
          return jedisPool;
        }
      }
    }
    return jedisPool;
  }
}
