package com.yqc.myproxy;

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
public interface MyInvocationHandler {

  Object invoke(Object proxy, Method method, Object[] args)
      throws InvocationTargetException, IllegalAccessException;
}
