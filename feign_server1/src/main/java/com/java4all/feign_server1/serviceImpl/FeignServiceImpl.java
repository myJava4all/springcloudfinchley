package com.java4all.feign_server1.serviceImpl;

import com.java4all.feign_server1.service.FeignService;
import org.springframework.stereotype.Component;

/**
 * description:
 * 此类用于feign中启动断路器时的熔断方法。
 * 不开启熔断器，此类无需添加
 *
 * @author wangzx
 * @version v1.0
 * @date 2018/11/23 17:19
 */
@Component
public class FeignServiceImpl implements FeignService{

  //熔断方法
  @Override
  public String getCompany(String id) {
    return "服务出错，返回指定企业信息：华为"+id;
  }
}
