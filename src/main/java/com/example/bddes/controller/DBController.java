package com.example.bddes.controller;

import com.example.bddes.service.DBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gao peng
 * @date 2019/7/30 17:36
 */
@RestController
public class DBController {
  @Autowired
  private DBService dbService;

  @RequestMapping(value = "/query/{schema}/{tableName}", method = RequestMethod.GET)
  @ResponseBody
  public String query(@PathVariable("schema") String schema, @PathVariable("tableName") String tableName) {

    dbService.query(schema, tableName);

    return "ok";
  }

  @RequestMapping(value = "/query/{schema}", method = RequestMethod.GET)
  @ResponseBody
  public String query(@PathVariable("schema") String schema) {

    dbService.query(schema, null);
    return "ok";
  }

}
