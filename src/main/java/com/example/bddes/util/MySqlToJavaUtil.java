package com.example.bddes.util;

import cn.hutool.core.util.StrUtil;

import java.util.Map;

/**
 * @author gao peng
 * @date 2018/9/27 17:46
 */
public class MySqlToJavaUtil {

  public static String tranMysqlTOJavaType(String dataType) {
    String tableType = "";
    if (dataType.equals("bigint")) {
      tableType = "Long";
    } else if (dataType.equals("tinyint") || dataType.equals("int")) {
      tableType = "Int";
    } else if (dataType.equals("varchar")) {
      tableType = "String";
    } else if (dataType.equals("datetime") || dataType.equals("date")) {
      tableType = "Timestamp";
    } else if (dataType.equals("double")) {
      tableType = "Double";
    } else {
      System.out.println("类型未匹配到，请注意...");
    }
    return tableType;
  }


  public static String tranMySQLTableToJavaBean(String tableName) {
    String mapperName = tableName.substring(2, tableName.length());
    mapperName = StrUtil.toCamelCase(mapperName);
    mapperName = StrUtil.upperFirst(mapperName);
    return mapperName;
  }

  /**
   * 数据库字段命名规则修改为JAVA驼峰格式
   * @return java.lang.String
   * @param: [columnName]
   * @author gao peng
   * @date 2018/9/28 14:01
   */
  public static String getJavaFieldName(String columnName) {
    String humpColumnName = StrUtil.toCamelCase(columnName);

    return StrUtil.upperFirst(humpColumnName);
  }

  /**
   * 判断是否为主键（目前根据名字判断），后续可以优化，通过数据库读取主键标识
   * @return boolean
   * @param: [map]
   * @author gao peng
   * @date 2018/9/28 14:00
   */
  public static boolean checkPrimary(Map map) {
    String tableName = (String) map.get("COLUMN_NAME");
    if (tableName.equals("id")) {
      return true;
    }
    return false;
  }
}
