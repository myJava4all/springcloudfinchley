package com.java4all.controller;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * description:
 *
 * @author wangzx
 * @version v1.0
 * @date 2018/11/23 14:07
 */
@RestController
@RequestMapping("user")
public class UserController {

  @GetMapping("get")
  public String getUser(String id){
    return "用户信息为：马云"+id;
  }
}
