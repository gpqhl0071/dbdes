package com.example.bddes.service;

import java.util.List;
import java.util.Map;

/**
 * @author gao peng
 * @date 2019/7/30 17:30
 */
public interface DBService {
  public List<Map<String, Object>> query(String schema, String tabelName, String tableComment);

  public List<Map<String, Object>> queryTabel(String tabelName, String columnComment);
}
