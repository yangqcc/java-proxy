package com.yqc.myproxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * <p>title:</p>
 * <p>description:</p>
 *
 * @author yangqc
 * @date Created in 2018-01-01
 * @modified By yangqc
 */
public class MyClassLoader extends ClassLoader {

  private File dir;

  public MyClassLoader(String path) {
    dir = new File(path);
  }

  @Override
  protected Class<?> findClass(String name) throws ClassNotFoundException {
    if (dir != null) {
      File clazzFile = new File(dir, name + ".class");
      if (clazzFile.exists()) {
        try (FileInputStream inputStream = new FileInputStream(clazzFile)) {
          ByteArrayOutputStream baos = new ByteArrayOutputStream();
          byte[] buffer = new byte[1024];
          int len;
          while ((len = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, len);
          }
          //最终把我们的流输出到jvm内存
          return defineClass("com.yqc.myproxy." + name, baos.toByteArray(), 0, baos.size());
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return super.findClass(name);
  }
}
