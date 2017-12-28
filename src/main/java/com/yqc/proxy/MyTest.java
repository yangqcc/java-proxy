package com.yqc.proxy;

import java.lang.reflect.Proxy;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author yangqc
 * @date Created in 2017-12-28
 * @modified By yangqc
 */
public class MyTest {

  public static void main(String[] args) {
    //这个方法返回的是一个代理类
    People people = (People) Proxy
        .newProxyInstance(People.class.getClassLoader(), new Class[]{People.class},
            new ZSParent(new Zhangsan()));
    people.zhaoduixiang();
  }
}
