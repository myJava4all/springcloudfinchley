package com.java4all.dao;

import com.java4all.entity.ProductStock;
import org.junit.runners.Parameterized.Parameter;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * description:
 *
 * @author wangzhongxiang
 * @date 2018/11/28 15:10
 */
@Repository
public interface ProductStockDao {

  ProductStock getById(Integer id);

  void updateStockById(@Param("id") Integer id,@Param("stock")Integer stock);
}
