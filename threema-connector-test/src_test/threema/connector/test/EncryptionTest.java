package threema.connector.test;

import org.junit.jupiter.api.Test;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import threema.connector.receiverData;
import threema.connector.sendThreemaMessageData;

@IvyProcessTest
public class EncryptionTest {
  private static final BpmProcess GET_ENCRYPTION_PROCESS = BpmProcess.path("encrptMessage");
  private static final BpmElement GET_ENCRYPTION_START = GET_ENCRYPTION_PROCESS.elementName("call(sendThreemaMessageData)");

  @Test
  void encryptMessage() {
    sendThreemaMessageData msgData = new sendThreemaMessageData();
    receiverData recData = new receiverData();



  }

}
