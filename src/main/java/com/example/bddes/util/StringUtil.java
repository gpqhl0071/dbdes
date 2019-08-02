package com.example.bddes.util;

/**
 * @author gao peng
 * @date 2019/8/2 9:48
 */
public class StringUtil {

  /**
   * 组装字符串，并换行
   * @return void
   * @param: [sb, str]
   * @author gao peng
   * @date 2018/9/28 14:00
   */
  public static void writeLine(StringBuffer sb, String str) {
    sb.append(str);
    StringUtil.enter(sb);
  }

  public static void enter(StringBuffer mapSB) {
    mapSB.append("</br>");
  }

  public static void splace(StringBuffer mapSB, int num) {
    for (int i = 0; i < num; i++) {
      mapSB.append(" ");
    }
  }

}
