package com.yqc.myproxy;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author yangqc
 * @date Created in 2017-12-30
 * @modified By yangqc
 */
public class MyProxy {

  /**
   * 定义换行符
   */
  static final String rt = "\r\n";

  /**
   * 创建出内存中的代理实例
   */
  public static Object newProxyInstance(ClassLoader loader, Class clazz,
      MyInvocationHandler myInvocationHandler) {
    Method[] methods = clazz.getMethods();
    //1.字符串拼凑的方式把内存代理类拼凑出来
    String proxyClass =
        "package com.yqc.myproxy;" + rt
            + "import java.lang.reflect.Method;" + rt
            + "public class $Proxy0 implements " + clazz.getName() + "{" + rt
            + "  private MyInvocationHandler h;" + rt
            + "public $Proxy0(MyInvocationHandler h){" + rt
            + "   this.h=h;" + rt + "}" + getMethodString(methods, clazz) + rt
            + "}";

    //2.用IO流的方式把java的String写入到文件中
    String fileName = "D:/IdeaProjects/java-proxy/$Proxy0.java";
    File f = new File(fileName);
    try (FileWriter fw = new FileWriter(f)) {
      fw.write(proxyClass);
      fw.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
    //3.编译生成的java文件
    JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
    StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
    Iterable units = fileManager.getJavaFileObjects(fileName);
    CompilationTask t = compiler.getTask(null, fileManager, null, null, null, units);
    t.call();
    try {
      fileManager.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    //4.把磁盘上的Class文件加载到内存中来
    MyClassLoader loader1 = new MyClassLoader("D:/IdeaProjects/java-proxy");
    try {
      //内存中的代理类的对象
      Class proxy0Clazz = loader1.findClass("$Proxy0");
      Constructor constructor = proxy0Clazz.getConstructor(MyInvocationHandler.class);
      Object object = constructor.newInstance(myInvocationHandler);
      return object;
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static String getMethodString(Method[] methods, Class clazz) {
    String proxyMe = "";
    for (Method method : methods) {
      proxyMe += "public void " + method.getName() + "() throws Throwable{" + rt
          + "Method method=" + clazz.getName() + ".class.getMethod(\"" + method.getName()
          + "\",new Class[]{});" + rt
          + "this.h.invoke(this,method,null);" + rt + "}" + rt;
    }
    return proxyMe;
  }
}
