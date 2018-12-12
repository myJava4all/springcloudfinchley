package com.java4all.redissonlock;

import com.java4all.zkLock.ZookeeperClient;
import java.util.concurrent.TimeUnit;
import org.redisson.Redisson;
import org.redisson.RedissonMultiLock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * description:
 *
 * @author wangzhongxiang
 * @date 2018/11/28 17:00
 */
@Component
public class RedissonDistributedLock implements IRedissonDistributedLock{

  private static final Logger logger = LoggerFactory.getLogger(ZookeeperClient.class);
  private static Redisson redisson = RedissonManager.getRedisson();

  public void lock(String lockKey){
    RLock lock = redisson.getLock(lockKey);
    lock.lock();
    logger.info("【Redisson lock】success to acquire lock for [ "+lockKey+" ]");
  }

  public void lock(String lockKey,Integer expireTime,TimeUnit timeUnit){
    RLock lock = redisson.getLock(lockKey);
    lock.lock(expireTime, timeUnit);
    logger.info("【Redisson lock】success to acquire lock for [ "+lockKey+" ],expire time:"+expireTime+timeUnit);

  }

  public void fairLock(String lockKey,Integer expireTime,TimeUnit timeUnit){
    RLock fairLock = redisson.getFairLock(lockKey);
    fairLock.lock(expireTime, timeUnit);
    logger.info("【Redisson lock】success to acquire fair lock for [ "+lockKey+" ],expire time:"+expireTime+timeUnit);
  }

  public void multiLock(Integer expireTime,TimeUnit timeUnit,String ...lockKey){
    RLock [] rLocks = new RLock[lockKey.length];
    for(int i = 0,length = lockKey.length; i < length ;i ++){
      RLock lock = redisson.getLock(lockKey[i]);
      rLocks[i] = lock;
    }
    RedissonMultiLock multiLock = new RedissonMultiLock(rLocks);
    multiLock.lock(expireTime,timeUnit);
    logger.info("【Redisson lock】success to acquire multiLock for [ "+lockKey+" ],expire time:"+expireTime+timeUnit);
  }

  public void unlock(String lockKey){
    RLock lock = redisson.getLock(lockKey);
    lock.unlock();
    logger.info("【Redisson lock】success to release lock for [ "+lockKey+" ]");
  }


}
