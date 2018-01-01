package com.yqc.myproxy;

import com.yqc.proxy.People;
import com.yqc.proxy.Zhangsan;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author yangqc
 * @date Created in 2017-12-30
 * @modified By yangqc
 */
public class MyTest {

  public static void main(String[] args) throws Throwable {
    People people = (People) MyProxy
        .newProxyInstance(People.class.getClassLoader(), People.class, new ZSParent(new Zhangsan()));
    people.zhaoduixiang();
  }
}
