package com.java4all.feign_server1.service;

import com.java4all.feign_server1.serviceImpl.FeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * description:
 *
 * @author wangzx
 * @version v1.0
 * @date 2018/11/23 15:02
 */
//通过此注解，来指定调用哪个服务
//如果开启了断路器，只需要添加fallback，在此接口实现类中添加熔断方法即可
@FeignClient(value = "company-server",fallback = FeignServiceImpl.class)
public interface FeignService {

  /**调用company-server服务中的company/get接口*/
  @GetMapping(value = "/company/get")
  String getCompany(@RequestParam(value = "id")String id);
}
