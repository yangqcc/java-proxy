package com.yqc.myproxy;

import com.yqc.proxy.People;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author yangqc
 * @date Created in 2017-12-30
 * @modified By yangqc
 */
public class ZSParent implements MyInvocationHandler {

  private People people;

  public ZSParent(People people) {
    this.people = people;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args)
      throws InvocationTargetException, IllegalAccessException {
    before();
    Object object = method.invoke(people, args);
    after();
    return object;
  }

  private void before() {
    System.out.println("method before!");
  }

  private void after() {
    System.out.println("method after!");
  }
}
