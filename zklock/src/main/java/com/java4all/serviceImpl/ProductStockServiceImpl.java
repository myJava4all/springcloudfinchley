package com.java4all.serviceImpl;

import com.java4all.dao.ProductStockDao;
import com.java4all.entity.ProductStock;
import com.java4all.redislock.RedisDistributedLock;
import com.java4all.redissonlock.IRedissonDistributedLock;
import com.java4all.redissonlock.RedissonDistributedLock;
import com.java4all.redissonlock.RedissonManager;
import com.java4all.service.ProductStockService;
import com.java4all.zkLock.ZkDistributedLock;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description:
 * 测试redis分布式锁
 * @author wangzhongxiang
 * @date 2018/11/28 15:10
 */
@Service
public class ProductStockServiceImpl implements ProductStockService {

  @Autowired
  private ProductStockDao productStockDao;
  @Autowired
  private IRedissonDistributedLock redissonDistributedLock;
  private static Redisson redisson = RedissonManager.getRedisson();

  @Override
  public ProductStock getById(Integer id) {
    return productStockDao.getById(id);
  }

  /**
   * 测试不加锁的情况
   * @param id
   * @param stock
   */
  @Override
  public void updateStockById(Integer id, Integer stock) {
    System.out.println("---------->"+Thread.currentThread().getName());

    ProductStock product = productStockDao.getById(id);
    Integer stock1 = product.getStock();
    stock1 = stock1+stock;
    productStockDao.updateStockById(id,stock1);
  }

  /***
   * 测试redis分布式锁
   * @param id
   * @param stock
   */
  @Override
  public void updateStockById1(Integer id, Integer stock) {
    System.out.println("---------->"+Thread.currentThread().getName());

    String randomValue = id+ UUID.randomUUID().toString();//随机值，确保全局唯一
    RedisDistributedLock.acquireLock(id.toString(),randomValue,5*1000);//加锁

    //业务逻辑
    ProductStock product = productStockDao.getById(id);
    Integer stock1 = product.getStock();
    stock1 = stock1+stock;
    productStockDao.updateStockById(id,stock1);

    RedisDistributedLock.releaseLock(id.toString(),randomValue);//释放锁
  }

  /**
   * 测试redisson分布式锁
   * @param id
   * @param stock
   */
  @Override
  public void updateStockById2(Integer id, Integer stock) {
    System.out.println("---------->"+Thread.currentThread().getName());

    try{
      redissonDistributedLock.lock(id.toString(),5,TimeUnit.SECONDS);
      ProductStock product = productStockDao.getById(id);
      Thread.sleep(500);
      Integer stock1 = product.getStock();
      stock1 = stock1+stock;
      productStockDao.updateStockById(id,stock1);
    }catch (Exception ex){
      ex.printStackTrace();
    }finally {
      redissonDistributedLock.unlock(id.toString());
    }

  }


  /**
   * 测试zookeeper分布式锁
   * @param id
   * @param stock
   */
  @Override
  public void updateStockById3(Integer id, Integer stock) {
    System.out.println("---------->"+Thread.currentThread().getName());

    try{
      ZkDistributedLock.acquire(id.toString(),10*1000, TimeUnit.MILLISECONDS);

      ProductStock product = productStockDao.getById(id);
      Thread.sleep(1000);
      Integer stock1 = product.getStock();
      stock1 = stock1+stock;
      productStockDao.updateStockById(id,stock1);

      ZkDistributedLock.release(id.toString());
    }catch (Exception ex){
      ex.printStackTrace();
    }


  }

}
