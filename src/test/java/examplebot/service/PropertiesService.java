package examplebot.service;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Provides key and values from example-bot.properties
 */
public class PropertiesService {

  public static final String PROPERTIES_FILE_PATH = "src/test/resources/example-bot.properties";

  private final Properties properties = new Properties();

  {
    try {
      properties.load(new FileInputStream(PROPERTIES_FILE_PATH));
    } catch (Exception e) {
      System.err.println("Error while reading "+PROPERTIES_FILE_PATH);
      throw new RuntimeException(e);
    }
  }

  public String getProperty(String key) {
    return properties.getProperty(key);
  }

}
