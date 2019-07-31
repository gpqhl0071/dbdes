package com.example.bddes;

import com.example.bddes.dao.DBDao;
import com.example.bddes.service.DBService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BddesApplicationTests {
  @Autowired
  private DBService dbServiceImpl;

  @Test
  public void contextLoads() {

  }

}
