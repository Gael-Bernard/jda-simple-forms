package fr.gbernard.jdaforms.utils;

public class ExceptionUtils {

  public static void uncheck(ExceptionRunner fun) {
    try {
      fun.run();
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T uncheck(ExceptionSupplier<T> supplier) {
    try {
      return supplier.get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public interface ExceptionRunner {
    void run() throws Exception;
  }

  public interface ExceptionSupplier<T> {
    T get() throws Exception;
  }

}
