package com.example.bddes.service;

/**
 * @author gao peng
 * @date 2019/8/2 9:59
 */
public interface QUIDService {

  public String generatorInsert(String schema, String tableName);

  public String generatorBatchInsert(String schema, String tableName);

  public String generatorQueryById(String schema, String tableName);

  public String generatorQueryPage(String schema, String tableName);
}
