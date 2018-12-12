package com.java4all.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author wangzx
 * @version v1.0
 * @date 2018/11/23 13:50
 */
@RestController
@RequestMapping("company")
public class CompanyController {

  @Value("${server.port}")
  String port;

  @GetMapping("get")
  public String getCompany(Integer id){
    return "port:"+port+",企业信息为：阿里巴巴"+id;
  }
}
