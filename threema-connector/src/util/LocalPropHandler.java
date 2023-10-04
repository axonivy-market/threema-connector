package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LocalPropHandler {

  public static String getProperty(String name) {
    try (InputStream input = new FileInputStream("src/resources/config.properties")) {
      Properties props = new Properties();
      props.load(input);
      return props.getProperty(name, "Empty");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "Property not found";
  }
}
