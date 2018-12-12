package com.java4all.redissonlock;

import java.util.concurrent.TimeUnit;

/**
 * description:
 *
 * @author wangzhongxiang
 * @date 2018/11/29 10:48
 */
public interface IRedissonDistributedLock {

  /**
   * 获取锁，需要主动释放
   * @param lockKey
   */
  void lock(String lockKey);

  /**
   * 获取锁，如果没有主动调用unlock解锁，expireTime后会自动释放
   * @param lockKey
   * @param expireTime 如果没有调用unlock解锁，expireTime 后自动释放
   * @param timeUnit 时间单位
   */
  void lock(String lockKey,Integer expireTime,TimeUnit timeUnit);

  /**
   * 获取公平锁，如果没有主动调用unlock解锁，expireTime后会自动释放
   * @param lockKey
   * @param expireTime 如果没有调用unlock解锁，expireTime 后自动释放
   * @param timeUnit 时间单位
   */
  void fairLock(String lockKey,Integer expireTime,TimeUnit timeUnit);

  /**
   * 获取一组锁，一组资源的锁，当作一个锁
   * @param expireTime
   * @param timeUnit 如果没有调用unlock解锁，expireTime 后自动释放
   * @param lockKey 时间单位
   */
  void multiLock(Integer expireTime,TimeUnit timeUnit,String ...lockKey);

  /**
   * 释放锁
   * @param lockKey
   */
  void unlock(String lockKey);

}
