package fr.gbernard.jdaforms.utils;

public class ExceptionUtils {

  public static <T> void uncheck(ExceptionRunner<T> fun) {
    try {
      fun.run();
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public interface ExceptionRunner<T> {
    public void run() throws Exception;
  }

}
