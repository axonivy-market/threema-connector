package apitool;

import static util.LocalPropHandler.getProperty;

public class ApiConnector {
  private String threemaId;
  private String secret;
  private String privateKey;

  public ApiConnector(){
    threemaId = getProperty("th.id");
    secret = getProperty("th.secret");
    privateKey = getProperty("th.privatekey");
  }

  public String getID(String email) {


    return "";
  }
}
