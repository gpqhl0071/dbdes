package com.example.bddes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gao peng
 * @date 2019/7/31 14:10
 */
@Controller
public class IndexController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String toDBIndex(){

    return "database/index";
  }

  @RequestMapping(value = "/toTableIndex", method = RequestMethod.GET)
  public ModelAndView toTableIndex(HttpServletRequest request, HttpServletResponse response, ModelAndView mv){
    String tableName = request.getParameter("tableName");
    String schema = request.getParameter("schema");

    mv.addObject("tableName", tableName);
    mv.addObject("schema", schema);
    mv.setViewName("/database/toTableIndex");

    return mv;
  }

}
