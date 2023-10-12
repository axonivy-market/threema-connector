package threema.connector.test;


import static org.assertj.core.api.Assertions.assertThat;
import javax.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.Ivy;
import ch.threema.apitool.CryptTool;
import threema.connector.receiverData;

@IvyProcessTest
public class MessageEncryptiontest {
  private static final BpmProcess GET_ENCRYPTION_PROCESS = BpmProcess.path("messageEncryption");

  private static String publicKey;
  private static String privateKey;

  @BeforeAll
  void generateKeys() {
    byte[] privateKeyBytes = new byte[32];
    byte[] publicKeyBytes = new byte[32];

    CryptTool.generateKeyPair(privateKeyBytes, publicKeyBytes);
    privateKey = DatatypeConverter.printHexBinary(privateKeyBytes);
    publicKey = DatatypeConverter.printHexBinary(publicKeyBytes);

    Ivy.log().debug(privateKey);
  }

  @Test
  void encryptMessage(BpmClient bpmClient) {
    Ivy.log().debug("are you running");
    BpmElement callable = GET_ENCRYPTION_PROCESS.elementName("call(receiverData)");
    receiverData recData = new receiverData();
    recData.setPlainMessage("Hello World");
    recData.setPublicKey(publicKey);


    ExecutionResult result = bpmClient.start().subProcess(callable).execute(recData);
    receiverData resultData = result.data().last();
    assertThat(resultData.getEncryptedMessage()).isNotEmpty();



  }

}
