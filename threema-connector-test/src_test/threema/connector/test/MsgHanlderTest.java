package threema.connector.test;

import static apitool.MsgHandler.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;


public class MsgHanlderTest {

	  @Test
	  public void generateKeys() {
		byte[][] keys = genKeyPair();	
		assertThat(keys.length).isEqualTo(2);
		  
		byte[] privKey = keys[0];
		byte[] pubKey = keys[1];
		assertThat(privKey).isNotEmpty();
		assertThat(pubKey).isNotEmpty();
	  }
	  
	  @Test
	  public void encryptMsg(){
		String plainMsg = "Hello World";
		byte[][] keys = genKeyPair();	
		byte[] privKey = keys[0];
		byte[] pubKey = keys[1];
		String encryptedMsg = encryptMessage(plainMsg, privKey, pubKey);
		assertThat(encryptedMsg).isNotBlank();
	  }

}
