package com.example.bddes.service.impl;

import cn.hutool.core.util.EscapeUtil;

import com.example.bddes.config.CommonConfig;
import com.example.bddes.dao.DBDao;
import com.example.bddes.service.QUIDService;
import com.example.bddes.util.MySqlToJavaUtil;
import com.example.bddes.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author gao peng
 * @date 2018/9/28 10:13
 */
@Service
public class QUIDServiceImpl implements QUIDService {

  @Autowired
  private DBDao dbDao;
  @Autowired
  private CommonConfig commonConfig;

  /**
   * 插入模板方法
   *
   * @return void
   * @param: [tableName]
   * @author gao peng
   * @date 2018/9/28 14:05
   */
  public String generatorInsert(String schema, String tableName) {

    StringBuffer sb = new StringBuffer();
    StringUtil.writeLine(sb, "");

    List<Map<String, Object>> list = dbDao.queryTable(schema, tableName, "", "");

    String mapperName = MySqlToJavaUtil.tranMySQLTableToJavaBean(tableName);


    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, "public " + mapperName + "Bean save(final " + mapperName + "Bean bean) {");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "KeyHolder keyHolder = new GeneratedKeyHolder();");
    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "jt.update(new PreparedStatementCreator() {");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 4);
    StringUtil.writeLine(sb, "@Override");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 4);
    StringUtil.writeLine(sb, "public PreparedStatement createPreparedStatement(java.sql.Connection paramConnection) throws SQLException {");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 8);
    insertSQL(tableName, sb, list);

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 8);
    StringUtil.writeLine(sb, "PreparedStatement ps = paramConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 8);
    StringUtil.writeLine(sb, "int index = 1;");

    for (Map map : list) {
      StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 8);

      getBeanName(sb, map, "bean");
    }

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 8);
    StringUtil.writeLine(sb, "return ps;");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 6);
    StringUtil.writeLine(sb, "}");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 4);
    StringUtil.writeLine(sb, "}, keyHolder);");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "bean.setId(keyHolder.getKey().intValue());");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "return bean;");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, "}");

    return sb.toString();
  }

  /**
   * 批量插入模板方法
   *
   * @return void
   * @param: [tableName]
   * @author gao peng
   * @date 2018/9/28 14:06
   */
  public String generatorBatchInsert(String schema, String tableName) {

    StringBuffer sb = new StringBuffer();
    StringUtil.writeLine(sb, "");

    List<Map<String, Object>> list = dbDao.queryTable(schema, tableName, "", "");

    String mapperName = MySqlToJavaUtil.tranMySQLTableToJavaBean(tableName);

    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, EscapeUtil.escapeHtml4("public void batchSave(final List<" + mapperName + "Bean> list) " +
        "{"));

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    insertSQL(tableName, sb, list);

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "jt.batchUpdate(sql, new BatchPreparedStatementSetter() {");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 4);
    StringUtil.writeLine(sb, "public void setValues(PreparedStatement ps, int i) throws SQLException {");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 6);
    StringUtil.writeLine(sb, "int index = 1;");

    for (Map map : list) {
      if (MySqlToJavaUtil.checkPrimary(map)) {
        continue;
      }

      StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 6);

      getBeanName(sb, map, "list.get(i)");
    }
    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 4);
    StringUtil.writeLine(sb, "}");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 4);
    StringUtil.writeLine(sb, "public int getBatchSize() {");
    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 6);
    StringUtil.writeLine(sb, "return list.size();");
    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 4);
    StringUtil.writeLine(sb, "}");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "});");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, "}");

    return sb.toString();
  }

  public String generatorQueryById(String schema, String tableName) {

    StringBuffer sb = new StringBuffer();
    StringUtil.writeLine(sb, "");

    List<Map<String, Object>> list = dbDao.queryTable(schema, tableName, "", "");

    String beanName = MySqlToJavaUtil.tranMySQLTableToJavaBean(tableName);

    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, "public " + beanName + "Bean queryById(int id) {");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "String sql = \"select * from " + tableName + " where id = ? \";");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "return jt.queryForObject(sql, new Object[]{id}, new " + beanName + "RowMapper());");


    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, "}");

    return sb.toString();
  }

  public String generatorQueryPage(String schema, String tableName) {

    StringBuffer sb = new StringBuffer();
    StringUtil.writeLine(sb, "");

    List<Map<String, Object>> list = dbDao.queryTable(schema, tableName, "", "");

    String beanName = MySqlToJavaUtil.tranMySQLTableToJavaBean(tableName);

    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, "public Page queryPage(int pageNo, int pageSize) {");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "String sql = \"select * from " + tableName + " order by id desc \";");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "List<Object> paramList = new ArrayList<Object>();");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "return this.queryForPage(sql.toString(), paramList.toArray(), pageNo, pageSize);");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, "}");

    return sb.toString();
  }

  public String generatorQueryByCondition(String schema, String tableName) {

    StringBuffer sb = new StringBuffer();
    StringUtil.writeLine(sb, "");

    List<Map<String, Object>> list = dbDao.queryTable(schema, tableName, "", "");

    String beanName = MySqlToJavaUtil.tranMySQLTableToJavaBean(tableName);


    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, EscapeUtil.escapeHtml4("public List<" + beanName + "Bean> queryByCondition(" + beanName + "Bean bean) {"));

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "List<Object> paramList = new ArrayList<Object>();");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "StringBuffer sql = new StringBuffer(\"select * from " + tableName + " where 1=1 \");");


    for (Map map : list) {
      String columnName = (String) map.get("COLUMN_NAME");
      String dataType = (String) map.get("DATA_TYPE");

      String fieldType = MySqlToJavaUtil.tranMysqlTOJavaType(dataType);
      String fieldName = MySqlToJavaUtil.getJavaFieldName(columnName);

      StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
      if (fieldType.equals("Long") || fieldType.equals("Int") || fieldType.equals("Double")) {
        StringUtil.writeLine(sb, "if (bean.get" + fieldName + "() != null) {");
      } else if (fieldType.equals("Timestamp")) {
        StringUtil.writeLine(sb, "if (bean.get" + fieldName + "() != null) {");
      } else {
        StringUtil.writeLine(sb, "if (StrUtil.isNotBlank(bean.get" + fieldName + "())) {");
      }

      StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 4);
      StringUtil.writeLine(sb, "sql.append(\" and " + columnName + " = ?\");");

      StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 4);
      StringUtil.writeLine(sb, "paramList.add(bean.get" + fieldName + "());");

      StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
      StringUtil.writeLine(sb, "}");
    }

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "return jt.query(sql.toString(), paramList.toArray(), new " + beanName + "RowMapper());");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, "}");

    return sb.toString();
  }

  public String generatorUpdate(String schema, String tableName) {

    StringBuffer sb = new StringBuffer();
    StringUtil.writeLine(sb, "");

    List<Map<String, Object>> list = dbDao.queryTable(schema, tableName, "", "");

    String beanName = MySqlToJavaUtil.tranMySQLTableToJavaBean(tableName);


    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, EscapeUtil.escapeHtml4("public int update(" + beanName + "Bean bean) {"));

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "List<Object> paramList = new ArrayList<Object>();");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "StringBuffer sql = new StringBuffer(\"update " + tableName + " set \");");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "StringBuffer condition = new StringBuffer();");


    for (Map map : list) {
      String columnName = (String) map.get("COLUMN_NAME");
      String dataType = (String) map.get("DATA_TYPE");

      String fieldType = MySqlToJavaUtil.tranMysqlTOJavaType(dataType);
      String fieldName = MySqlToJavaUtil.getJavaFieldName(columnName);

      if (fieldName.equals("Id")) {
        continue;
      }

      StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
      if (fieldType.equals("Long") || fieldType.equals("Int") || fieldType.equals("Double")) {
        StringUtil.writeLine(sb, "if (bean.get" + fieldName + "() != null) {");
      } else if (fieldType.equals("Timestamp")) {
        StringUtil.writeLine(sb, "if (bean.get" + fieldName + "() != null) {");
      } else {
        StringUtil.writeLine(sb, "if (StrUtil.isNotBlank(bean.get" + fieldName + "())) {");
      }

      StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 4);
      StringUtil.writeLine(sb, "condition.append(\" " + columnName + " = ? , \");");

      StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 4);
      StringUtil.writeLine(sb, "paramList.add(bean.get" + fieldName + "());");

      StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
      StringUtil.writeLine(sb, "}");
    }

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "sql.append(StrUtil.subBefore(condition.toString(), \",\", true));");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "sql.append(\" where id = ?\");");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "paramList.add(bean.getId());");
    StringUtil.splace(sb, commonConfig.getSpaceInitNum() + 2);
    StringUtil.writeLine(sb, "return jt.update(sql.toString(), paramList.toArray());");

    StringUtil.splace(sb, commonConfig.getSpaceInitNum());
    StringUtil.writeLine(sb, "}");

    return sb.toString();
  }

  private void getBeanName(StringBuffer sb, Map map, String param1) {
    String columnName = (String) map.get("COLUMN_NAME");
    String dataType = (String) map.get("DATA_TYPE");

    String fieldType = MySqlToJavaUtil.tranMysqlTOJavaType(dataType);
    String fieldName = MySqlToJavaUtil.getJavaFieldName(columnName);
    if (fieldName.equals("Id")) {
      return;
    }

    if (fieldType.equals("Timestamp")) {
      String par1 = "new Timestamp(" + param1 + ".get" + fieldName + "().getTime())";
      StringUtil.writeLine(sb, "ps.set" + fieldType + "(index++, " + par1 + ");");
    } else if (fieldType.equals("Int")) {
      // ps.setInt(index++, bean.getIsValid() == null ? 0 : bean.getIsValid());
      StringUtil.writeLine(sb, "ps.set" + fieldType + "(index++, "
          + param1 + ".get" + fieldName + "() == null ? 0 : "
          + param1 + ".get" + fieldName + "());");

    } else {
      StringUtil.writeLine(sb, "ps.set" + fieldType + "(index++, " + param1 + ".get" + fieldName + "());");
    }
  }

  private void insertSQL(String tableName, StringBuffer sb, List<Map<String, Object>> list) {
    StringUtil.writeLine(sb, "String sql = \" insert into "
        + tableName
        + " ("
        + getNames(list)
        + ") values ("
        + getValues(list)
        + ") \"; ");
  }

  private String getNames(List<Map<String, Object>> list) {
    StringBuffer sb = new StringBuffer();
    for (Map map : list) {

      String columnName = (String) map.get("COLUMN_NAME");
      if (columnName.equals("id")) {
        continue;
      }
      sb.append("`");
      sb.append(columnName);
      sb.append("`,");
    }
    return sb.toString().substring(0, sb.toString().length() - 1);
  }

  private String getValues(List<Map<String, Object>> list) {
    StringBuffer sb = new StringBuffer();
    for (Map map : list) {
      sb.append("?");
      sb.append(",");
    }
    return sb.toString().substring(0, sb.toString().length() - 3);
  }

}
