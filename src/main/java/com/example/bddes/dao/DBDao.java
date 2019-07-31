package com.example.bddes.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author gao peng
 * @date 2019/7/30 17:15
 */
public interface DBDao {

  public List<Map<String, Object>> query(@Param("schema") String schema,
      @Param("tableName") String tableName,
      @Param("tableComment") String tableComment);


  public List<Map<String, Object>> queryTable(
      @Param("tableName") String tableName,
      @Param("columnComment") String columnComment);
}
