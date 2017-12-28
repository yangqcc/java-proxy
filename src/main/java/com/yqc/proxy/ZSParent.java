package com.yqc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 实现了一个InvocationHandler的类，就是一个通知类
 *
 * @author yangqc
 */
public class ZSParent implements InvocationHandler {

  private People people;

  public ZSParent(People people) {
    this.people = people;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    //只是代理zhaoduixiang方法
    if (method.getName().equals("zhaoduixiang")) {
      //before不是张三的方法，是张三的父母帮他做的事情，是一个代理方法
      before();
      people.zhaoduixiang();
      //这是一个代理方法，在张三的方法调用完后，帮他做一些工作
      after();
      return null;
    }
    //其他的方法不用处理
    else {
      return method.invoke(people, args);
    }
  }

  private void before() {
    System.out.println("我是张三父母，我们在他之前帮他找好对象!");
  }

  private void after() {
    System.out.println("我是张三父母，张三生完小孩，我们帮他带小孩!");
  }
}