package threema.connector.test;

import static apiConnection.MsgHandler.encryptMessage;
import static apiConnection.MsgHandler.genKeyPair;
import static org.assertj.core.api.Assertions.assertThat;
import javax.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;

public class MsgHandlerTest {

  @Test
  public void generateKeys() {

    String key = "7858e5cadef8f3109ad19f28595eef3683803554830eb4cfe1d85db7093134a4";
    byte[] enc = DatatypeConverter.parseHexBinary(key);


    System.out.println(enc.length);


    byte[][] keys = genKeyPair();
    assertThat(keys.length).isEqualTo(2);
    System.out.println(keys[0].length);
    byte[] privKey = keys[0];
    byte[] pubKey = keys[1];
    assertThat(privKey).isNotEmpty();
    assertThat(pubKey).isNotEmpty();
  }

  @Test
  public void encryptMsg() {
    String plainMsg = "Hello World";
    byte[][] keys = genKeyPair();
    byte[] privKey = keys[0];
    byte[] pubKey = keys[1];
    String encryptedMsg = encryptMessage(plainMsg, privKey, pubKey);
    assertThat(encryptedMsg).isNotBlank();
  }
}