package threema.connector.test;


import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import threema.connector.receiverData;

@IvyProcessTest
public class MessageEncryptiontest {
  private final static BpmProcess ENCRYPTION_PROCESS = BpmProcess.name("messageEncryption");
  private final static String PUBLIC_KEY = "ffbb40cfced42f75c4d83c7d35300c0698bf3ef1ab49ace323a1bbc38ee23f36";
  private final static String PRIVATE_KEY = "ff364c727068fd6e3e6a711918393fa37649d902402a8eb31af108e79f625d82";


  @Test
  void encryptMessage(BpmClient bpmClient) {
    BpmElement callable = ENCRYPTION_PROCESS.elementName("call(receiverData)");

    receiverData recData = new receiverData();
    recData.setPlainMessage("Hello World");
    recData.setPublicKey(PUBLIC_KEY);


    ExecutionResult result = bpmClient.start().subProcess(callable).execute(recData);
    receiverData resultData = result.data().last();

    assertThat(resultData.getEncryptedMessage()).isNotEmpty();
  }
}
