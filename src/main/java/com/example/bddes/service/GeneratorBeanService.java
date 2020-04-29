package com.example.bddes.service;

/**
 * @author gao peng
 * @date 2019/8/1 16:52
 */
public interface GeneratorBeanService {

  public String handle(String schema, String tableName);

  public String generatorJDBCMapper(String schema, String tableName);

  public String genImport(String tableName);

  public String genClassName(String tableName);

  public String genEnd();
}
