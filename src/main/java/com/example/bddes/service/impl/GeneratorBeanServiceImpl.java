package com.example.bddes.service.impl;

import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.StrUtil;

import com.example.bddes.config.CommonConfig;
import com.example.bddes.dao.DBDao;
import com.example.bddes.service.GeneratorBeanService;
import com.example.bddes.util.MySqlToJavaUtil;
import com.example.bddes.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.List;
import java.util.Map;

/**
 * 数据库中字段，映射成java BEAN
 *
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
        type = "Integer";
      } else if (type.equals("Timestamp")) {
        type = "Date";
      } else if (type.equals("Long")) {
        type = "Long";
      }

      sb.append("/**</br>");
      sb.append(" * " + comment + "</br>");
      sb.append(" */</br>");
      sb.append("private " + type + " " + StrUtil.toCamelCase(columnName) + "; </br>");
    }

    return sb.toString();
  }

  public String genImport(String tableName) {
    String mapperName = MySqlToJavaUtil.tranMySQLTableToJavaBean(tableName);
    StringBuffer importBuffer = new StringBuffer();

    importBuffer.append("package com.redhorse.dao;").append("</br>");

    importBuffer.append("import cn.hutool.core.util.StrUtil;").append("</br>");
    importBuffer.append("import com.redhorse.bean.Page;").append("</br>");
    importBuffer.append("import com.redhorse.bean." + mapperName + "Bean;").append("</br>");
    importBuffer.append("import org.springframework.jdbc.core.BatchPreparedStatementSetter;").append("</br>");
    importBuffer.append("import org.springframework.jdbc.core.PreparedStatementCreator;").append("</br>");
    importBuffer.append("import org.springframework.jdbc.core.RowMapper;").append("</br>");
    importBuffer.append("import org.springframework.jdbc.support.GeneratedKeyHolder;").append("</br>");
    importBuffer.append("import org.springframework.jdbc.support.KeyHolder;").append("</br>");
    importBuffer.append("import org.springframework.stereotype.Repository;").append("</br>");
    importBuffer.append("import java.sql.PreparedStatement;").append("</br>");
    importBuffer.append("import java.sql.ResultSet;").append("</br>");
    importBuffer.append("import java.sql.SQLException;").append("</br>");
    importBuffer.append("import java.sql.Statement;").append("</br>");
    importBuffer.append("import java.sql.Timestamp;").append("</br>");
    importBuffer.append("import java.util.ArrayList;").append("</br>");
    importBuffer.append("import java.util.List;").append("</br>");

    return importBuffer.toString();
  }

  public String genClassName(String tableName) {
    String mapperName = MySqlToJavaUtil.tranMySQLTableToJavaBean(tableName);
    StringBuffer sb = new StringBuffer();
    sb.append("@Repository");
    StringUtil.enter(sb);
    sb.append("public class " + mapperName + "Dao extends BaseDao {");

    return sb.toString();
  }

  public String genEnd() {
    StringBuffer sb = new StringBuffer();
    sb.append("}");

    return sb.toString();
  }

  /**
   * 生成JDBCTemplate mapper方法
   *
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
