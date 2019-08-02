package com.example.bddes.service.impl;

import com.example.bddes.dao.DBDao;
import com.example.bddes.service.DBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author gao peng
 * @date 2019/7/30 17:31
 */
@Service
@SuppressWarnings("all")
public class DBServiceImpl implements DBService {
  @Autowired  // RedisTemplate，可以进行所有的操作
  private RedisTemplate<Object, Object> redisTemplate;
  @Autowired
  private DBDao dbDao;

  @Override
  public List<Map<String, Object>> query(String schema, String tabelName, String tableComment) {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    String key = "schema_" + schema + "_" + tableComment + "_" + tabelName;

    try {
      if (redisTemplate.hasKey(key)) {
        list = (List<Map<String, Object>>) redisTemplate.opsForValue().get(key);
      } else {
        list = dbDao.query(schema, tabelName, tableComment);
        redisTemplate.opsForValue().set(key, list);
        redisTemplate.expire(key, 1, TimeUnit.MINUTES);
      }
    } catch (Exception e) {
      // redis 链接异常
      list = dbDao.query(schema, tabelName, tableComment);
    }

    System.out.println(list.size());
    return list;
  }

  @Override
  public List<Map<String, Object>> queryTabel(String schema, String tabelName, String columnComment,
      String columnName) {
    String key = "table_" + schema + "_" + tabelName;
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    try {
      if (redisTemplate.hasKey(key)) {
        list = (List<Map<String, Object>>) redisTemplate.opsForValue().get(key);
      } else {
        list = dbDao.queryTable(schema, tabelName, columnComment, columnName);
        redisTemplate.opsForValue().set(key, list);
        redisTemplate.expire(key, 1, TimeUnit.MINUTES);
      }
    } catch (Exception e) {
      // redis 链接异常
      list = dbDao.queryTable(schema, tabelName, columnComment, columnName);
    }

    return list;
  }
}
