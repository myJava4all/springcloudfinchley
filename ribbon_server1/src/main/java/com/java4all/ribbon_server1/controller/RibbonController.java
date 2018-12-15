package com.java4all.ribbon_server1.controller;

import com.java4all.ribbon_server1.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author wangzx
 * @version v1.0
 * @date 2018/11/23 16:00
 */
@RestController
//@RequestMapping(value = "ribbon")
public class RibbonController {

  @Autowired
  private RibbonService ribbonService;

  @GetMapping(value = "getCompany")
  public String getCompany(String id,String token){
    String company = ribbonService.getCompany(id);
    System.out.println(company);
    return company;
  }
}
