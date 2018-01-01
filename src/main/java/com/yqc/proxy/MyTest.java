package com.yqc.proxy;

import java.io.FileOutputStream;
import java.lang.reflect.Proxy;
import sun.misc.ProxyGenerator;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author yangqc
 * @date Created in 2017-12-28
 * @modified By yangqc
 */
public class MyTest {

  public static void main(String[] args) throws Throwable {
    //这个方法返回的是一个代理类
    People people = (People) Proxy
        .newProxyInstance(People.class.getClassLoader(), new Class[]{People.class},
            new ZSParent(new Zhangsan()));
    people.zhaoduixiang();
//    createProxyClassFile();
    System.out.println("=======");
    System.out.println(people);
  }

  public static void createProxyClassFile() {
    byte[] data = ProxyGenerator.generateProxyClass("$Proxy0.class", new Class[]{People.class});
    try (FileOutputStream out = new FileOutputStream("$Proxy0.class")) {
      out.write(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
