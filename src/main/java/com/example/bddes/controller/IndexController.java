package com.example.bddes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author gao peng
 * @date 2019/7/31 14:10
 */
@Controller
public class IndexController {

  @RequestMapping(value = "/toDBIndex", method = RequestMethod.GET)
  public String toDBIndex(){

    return "database/index";
  }

  @RequestMapping(value = "/toTableIndex", method = RequestMethod.GET)
  public String toTableIndex(){

    return "database/toTableIndex";
  }

}
