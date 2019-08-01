package com.example.bddes.controller;

import cn.hutool.json.JSONUtil;

import com.example.bddes.service.DBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author gao peng
 * @date 2019/7/30 17:36
 */
@Controller
public class DBController {
  @Autowired
  private DBService dbService;

  @RequestMapping(value = "/db/query", method = RequestMethod.GET)
  @ResponseBody
  public String toDBIndex(Model model, HttpServletResponse response,
      HttpServletRequest request) throws Exception {

    String schema = request.getParameter("schema");
    String tableName = request.getParameter("tableName");
    String tableComment = request.getParameter("tableComment");

    List<Map<String, Object>> resultList = dbService.query(schema, tableName, tableComment);

//    model.addAttribute("list", resultList);

//    return "database/dbMain";
    return JSONUtil.toJsonStr(resultList);
  }

  @RequestMapping(value = "/db/queryTable", method = RequestMethod.GET)
  @ResponseBody
  public String queryTable(HttpServletRequest request, HttpServletResponse response) {

    String schema = request.getParameter("schema");
    String tableName = request.getParameter("tableName");
    String columnComment = request.getParameter("columnComment");
    String columnName = request.getParameter("columnName");
    List<Map<String, Object>> resultList = dbService.queryTabel(schema, tableName, columnComment, columnName);

    return JSONUtil.toJsonStr(resultList);
  }

}
