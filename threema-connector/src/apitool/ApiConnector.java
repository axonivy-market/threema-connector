package apitool;

import static util.LocalPropHandler.*;

public class ApiConnector {

  public static String getID(String email) {
    String id = getProperty("th.id");
    System.out.println(id);
    return "";
  }

  public static void main(String[] args) {
    getID("");
  }
}
