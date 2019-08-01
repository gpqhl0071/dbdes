package com.example.bddes.service.impl;

import cn.hutool.core.util.StrUtil;

import com.example.bddes.dao.DBDao;
import com.example.bddes.service.GeneratorBeanService;
import com.example.bddes.util.MySqlToJavaUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 数据库中字段，映射成java BEAN
 * @author gao peng
 * @date 2018/9/30 11:00
 */
@Service
@SuppressWarnings("all")
public class GeneratorBeanServiceImpl implements GeneratorBeanService {

  @Autowired
  private DBDao dbDao;

  public String handle(String schema, String tableName) {

    List<Map<String, Object>> list = dbDao.queryTable(schema, tableName, "", "");

    StringBuffer sb = new StringBuffer();

    for (Map<String, Object> map : list) {
      String columnName = (String) map.get("COLUMN_NAME");
      String dataType = (String) map.get("DATA_TYPE");
      String comment = (String) map.get("COLUMN_COMMENT");
      String type = MySqlToJavaUtil.tranMysqlTOJavaType(dataType);

      if (type.equals("Int")) {
        type = "int";
      } else if (type.equals("Timestamp")) {
        type = "Date";
      }

      sb.append("/**</br>");
      sb.append(" * " + comment + "</br>");
      sb.append(" */</br>");
      sb.append("private " + type + " " + StrUtil.toCamelCase(columnName) + "; </br>");
    }

    return sb.toString();
  }
}
