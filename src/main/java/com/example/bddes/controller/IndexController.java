package com.example.bddes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gao peng
 * @date 2019/7/31 14:10
 */
@Controller
public class IndexController {

  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String toDBIndex() {

    return "database/index";
  }

  @RequestMapping(value = "/toTableIndex", method = RequestMethod.GET)
  public String toTableIndex(HttpServletRequest request, HttpServletResponse response, Model m) {
    String tableName = request.getParameter("tableName");
    String schema = request.getParameter("schema");

    m.addAttribute("tableName", tableName);
    m.addAttribute("schema", schema);

    return "database/toTableIndex";
  }

}
