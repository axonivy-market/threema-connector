package apiConnection;

import ch.threema.apitool.APIConnector;
import ch.threema.apitool.PublicKeyStore;
import ch.threema.apitool.exceptions.ApiException;
import ch.threema.apitool.utils.ApiResponse;

public class Connector {
  private String threemaId;
  private String secret;
  private String privateKey;
  private String publicKey;
  private APIConnector tool;

  /*
   * th.id=*IVYDEV0
   * th.secret=riQNL7ajASmLCHD9
   * th.privateKey=7858e5cadef8f3109ad19f28595eef3683803554830eb4cfe1d85db7093134a4
   * th.publicKey=7b60359098863205c544865726e22396e2603c
   */

  public Connector(){
    /*
      threemaId = getProperty("th.id");
      secret = getProperty("th.secret");
      privateKey = getProperty("th.privateKey");
      publicKey = getProperty("th.publicKey");
    */
    threemaId = "*IVYDEV0";
    secret = "riQNL7ajASmLCHD9";
    privateKey = "7858e5cadef8f3109ad19f28595eef3683803554830eb4cfe1d85db7093134a4";
    publicKey = "7b60359098863205c544865726e22396e2603c";
    tool = new APIConnector(threemaId, secret, new PublicKeyStore() {
      @Override
      protected byte[] fetchPublicKey(String id) {
        //TODO: Implementation
        return null;
      }

      @Override
      protected void save(String id, byte[] pubKey) {
        //TODO: Implementation
      }
    });
  }

  public String getID(String email) {
    String id = "not found";
    ApiResponse<String> resp = null;
    try {
      resp = tool.lookupEmail(email);
      id = resp.getData();
    }catch(ApiException e) {
      String cause = e.getMessage();
      System.out.println(cause);

    }
    return id;
  }

}