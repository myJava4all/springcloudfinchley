package com.java4all.feign_server1.controller;

import com.java4all.feign_server1.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author wangzx
 * @version v1.0
 * @date 2018/11/23 15:00
 */
@RestController
//@RequestMapping(value = "feign")
public class FeignController {

  //编译报错，可以不用管，程序启动时才会注入此bean
  @Autowired
  FeignService feignService;

  @GetMapping("getCompany")
  public String getCompany(String id){
    String company = feignService.getCompany(id);
    System.out.println(company);
    return company;
  }
}
