package com.java4all.hystrix_ribbon_server1.controller;

import com.java4all.hystrix_ribbon_server1.service.HrsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author wangzx
 * @version v1.0
 * @date 2018/11/23 16:00
 */
@RestController
@RequestMapping(value = "hrs")
public class HrsController {

  @Autowired
  private HrsService hrsService;

  @GetMapping(value = "getCompany")
  public String getCompany(String id){
    String company = hrsService.getCompany(id);
    System.out.println(company);
    return company;
  }
}
