package com.java4all.redislock;

import com.java4all.zkLock.ZookeeperClient;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

/**
 * description:
 * 分布式锁
 * @author wangzhongxiang
 * @date 2018/11/27 10:46
 */
@Component
public class RedisDistributedLock {
  private static final Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);
  private static final String LOCK_SUCCESS = "OK";
  private static final Integer RELEASE_SUCCESS = 1;
  private static final String SET_IF_NOT_EXIST = "NX";
  private static final String SET_WITH_EXPIRE_TIME = "PX";
  private static JedisPool jedisPool = JedisManager.getJedisPool();

  /**
   * 加锁
   *
   * @param lockName 锁名，对应被争用的共享资源
   * @param randomValue 随机值，需要保持全局唯一，便于释放时校验锁的持有者
   * @param expireTime 过期时间，到期后自动释放，防止出现问题时死锁，资源无法释放
   * @return
   */
  public static boolean acquireLock(String lockName,String randomValue,int expireTime){
    Jedis jedis = jedisPool.getResource();
    try {
      while (true){
        String result = jedis
            .set(lockName, randomValue, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if(LOCK_SUCCESS.equals(result)){
          logger.info("【Redis lock】success to acquire lock for [ "+lockName+" ],expire time:"+expireTime+"ms");
          return true;
        }
      }
    }catch (Exception ex){
      ex.printStackTrace();
    }finally {
      if(null != jedis){
        jedis.close();
      }
    }
    logger.info("【Redis lock】failed to acquire lock for [ "+lockName+" ]");
    return false;
  }

  /**
   * redis释放锁
   * watch和muti命令保证释放时的对等性，防止误解锁
   *
   * @param lockName 锁名，对应被争用的共享资源
   * @param randomValue 随机值，需要保持全局唯一，以检验锁的持有者
   * @return 是否释放成功
   */
  public static boolean releaseLock(String lockName,String randomValue){
    Jedis jedis = jedisPool.getResource();
    try{
      jedis.watch(lockName);//watch监控
      if(randomValue.equals(jedis.get(lockName))){
        Transaction multi = jedis.multi();//开启事务
        multi.del(lockName);//添加操作到事务
        List<Object> exec = multi.exec();//执行事务
        if(RELEASE_SUCCESS.equals(exec.size())){
          logger.info("【Redis lock】success to release lock for [ "+lockName+" ]");
          return true;
        }
      }
    }catch (Exception ex){
      logger.info("【Redis lock】failed to release lock for [ "+lockName+" ]");
      ex.printStackTrace();
    }finally {
      if(null != jedis){
        jedis.unwatch();
        jedis.close();
      }
    }
    return false;
  }

}
