package com.example.bddes.service.impl;

import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.StrUtil;

import com.example.bddes.config.CommonConfig;
import com.example.bddes.dao.DBDao;
import com.example.bddes.service.GeneratorBeanService;
import com.example.bddes.util.MySqlToJavaUtil;
import com.example.bddes.util.StringUtil;

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
  @Autowired
  private CommonConfig commonConfig;

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
      } else if (type.equals("Long")) {
        type = "long";
      }

      sb.append("/**</br>");
      sb.append(" * " + comment + "</br>");
      sb.append(" */</br>");
      sb.append("private " + type + " " + StrUtil.toCamelCase(columnName) + "; </br>");
    }

    return sb.toString();
  }

  /**
   * 生成JDBCTemplate mapper方法
   * @return void
   * @param: [tableName]
   * @author gao peng
   * @date 2018/9/27 17:50
   */
  public String generatorJDBCMapper(String schema, String tableName) {
    StringBuffer mapSB = new StringBuffer();
    StringUtil.enter(mapSB);

    List<Map<String, Object>> list = dbDao.queryTable(schema, tableName, "", "");

    String mapperName = MySqlToJavaUtil.tranMySQLTableToJavaBean(tableName);

    StringUtil.splace(mapSB, commonConfig.getSpaceInitNum());
    mapSB.append(EscapeUtil.escapeHtml4("private class " + mapperName + "RowMapper implements RowMapper<" + mapperName + "Bean> {"));


    StringUtil.enter(mapSB);

    StringUtil.splace(mapSB, commonConfig.getSpaceInitNum());
    mapSB.append("@Override");
    StringUtil.enter(mapSB);

    StringUtil.splace(mapSB, commonConfig.getSpaceInitNum());
    mapSB.append("public " + mapperName + "Bean mapRow(ResultSet rs, int arg1) throws SQLException {");
    StringUtil.enter(mapSB);

    StringUtil.splace(mapSB, commonConfig.getSpaceInitNum());
    mapSB.append(mapperName + "Bean bean = new " + mapperName + "Bean();");
    StringUtil.enter(mapSB);

    for (Map map : list) {

      StringUtil.splace(mapSB, commonConfig.getSpaceInitNum() + 4);

      String bean = genBeanName(map);

      mapSB.append(bean);
      StringUtil.enter(mapSB);
    }

    StringUtil.splace(mapSB, commonConfig.getSpaceInitNum() + 4);
    mapSB.append("return bean;");

    StringUtil.enter(mapSB);
    StringUtil.splace(mapSB, commonConfig.getSpaceInitNum() + 2);
    mapSB.append("}");

    StringUtil.enter(mapSB);
    StringUtil.splace(mapSB, commonConfig.getSpaceInitNum());
    mapSB.append("}");

    return mapSB.toString();
  }


  private String genBeanName(Map map) {
    String columnName = (String) map.get("COLUMN_NAME");
    String dataType = (String) map.get("DATA_TYPE");

    String fieldType = MySqlToJavaUtil.tranMysqlTOJavaType(dataType);
    String fileName = MySqlToJavaUtil.getJavaFieldName(columnName);

    String param1 = "rs.get" + fieldType + "(\"" + columnName + "\")";

    return "bean.set" + fileName + "(" + param1 + ");";
  }

}
