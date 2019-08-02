package com.example.bddes.controller;

import cn.hutool.json.JSONUtil;

import com.example.bddes.service.DBService;
import com.example.bddes.service.GeneratorBeanService;
import com.example.bddes.service.QUIDService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
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
  @Autowired
  private GeneratorBeanService generatorBeanService;
  @Autowired
  private QUIDService quidService;

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

  /**
   * 生成BEAN对象
   * @return java.lang.String
   * @param: [request, response]
   * @author gao peng
   * @date 2019/8/1 16:56
   */
  @RequestMapping(value = "/db/genBean", method = RequestMethod.GET)
  @ResponseBody
  public String genBean(HttpServletRequest request, HttpServletResponse response) {

    String schema = request.getParameter("schema");
    String tableName = request.getParameter("tableName");

    String result = generatorBeanService.handle(schema, tableName);

    Map<String, String> resultMap = new HashMap<String, String>();
    resultMap.put("data", result);

    return JSONUtil.toJsonPrettyStr(resultMap);
  }

  @RequestMapping(value = "/db/genJDBCTemplate", method = RequestMethod.GET)
  @ResponseBody
  public String generatorJDBCTemplate(HttpServletRequest request, HttpServletResponse response) {

    String schema = request.getParameter("schema");
    String tableName = request.getParameter("tableName");

    StringBuffer sb = new StringBuffer();
    sb.append(generatorBeanService.generatorJDBCMapper(schema, tableName));
    sb.append(quidService.generatorBatchInsert(schema, tableName));
    sb.append(quidService.generatorInsert(schema, tableName));
    sb.append(quidService.generatorQueryById(schema, tableName));
    sb.append(quidService.generatorQueryPage(schema, tableName));

    Map<String, String> resultMap = new HashMap<String, String>();
    resultMap.put("data", sb.toString());

    return JSONUtil.toJsonPrettyStr(resultMap);
  }
}
