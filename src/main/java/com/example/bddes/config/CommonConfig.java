package com.example.bddes.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author gao peng
 * @date 2019/8/2 10:05
 */
@ConfigurationProperties(prefix = "common.config")
public class CommonConfig {

  private int spaceInitNum;

  public int getSpaceInitNum() {
    return spaceInitNum;
  }

  public void setSpaceInitNum(int spaceInitNum) {
    this.spaceInitNum = spaceInitNum;
  }
}
