package com.example.bddes.service.impl;

import com.example.bddes.dao.DBDao;
import com.example.bddes.service.DBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author gao peng
 * @date 2019/7/30 17:31
 */
@Service
public class DBServiceImpl implements DBService {
  @Autowired
  private DBDao dbDao;

  @Override
  public List<Map<String, Object>> query(String schema, String tabelName) {
    List list = dbDao.query(schema, tabelName);
    System.out.println(list.size());
    return list;
  }
}
