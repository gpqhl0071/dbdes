package com.example.bddes.util;

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
}
