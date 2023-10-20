package threema.connector.test.process;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.History;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.threema.mocks.ThreemaServiceMock;
import threema.connector.ReceiverData;
import util.LookupType;

@IvyProcessTest(enableWebServer = true)
public class GetReceiverInfoTest {

  private static final BpmProcess RECEIVER_INFO_PROCESS = BpmProcess.name("getReceiverInfo");
  private static final String VALID_ID = "validId";

  @BeforeEach
  void setup(AppFixture fixture) {
    fixture.config("RestClients.ThreemaGateway.Url", ThreemaServiceMock.URI);
  }

  @Test
  void getIDByValidEmail(BpmClient bpmClient) {
    BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
    String email = VALID_ID;
    ReceiverData recDatMail = new ReceiverData();
    recDatMail.setIdentifier(email);
    recDatMail.setType(LookupType.EMAIL);
    ExecutionResult resultMail = bpmClient.start().subProcess(callable).execute(recDatMail);
    ReceiverData resultDataMail = resultMail.data().last();
    History historyMail = resultMail.history();
    assertThat(resultDataMail.getApiResponse()).contains("200");
    assertThat(historyMail.elementNames())
            .contains("call(receiverData)")
            .contains("LookupId")
            .contains("LookupPubKey");
  }

  @Test
  void getIDByInvalidEmail(BpmClient bpmClient) {
    BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
    String email = "invalid@email.com";
    ReceiverData recDatMail = new ReceiverData();
    recDatMail.setIdentifier(email);
    recDatMail.setType(LookupType.EMAIL);
    ExecutionResult resultMail = bpmClient.start().subProcess(callable).execute(recDatMail);
    ReceiverData resultDataMail = resultMail.data().last();
    History historyMail = resultMail.history();
    assertThat(resultDataMail.getApiResponse()).contains("404");
    assertThat(historyMail.elementNames()).contains("call(receiverData)")
            .contains("LookupId")
            .doesNotContain("LookupPubKey");
  }

  @Test
  void getIDByValidPhone(BpmClient bpmClient) {
    BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
    String phone = VALID_ID;
    ReceiverData recDatMail = new ReceiverData();
    recDatMail.setIdentifier(phone);
    recDatMail.setType(LookupType.PHONE);
    ExecutionResult resultMail = bpmClient.start().subProcess(callable).execute(recDatMail);
    ReceiverData resultDataMail = resultMail.data().last();
    History historyMail = resultMail.history();
    assertThat(resultDataMail.getApiResponse()).contains("200");
    assertThat(historyMail.elementNames()).contains("call(receiverData)")
            .contains("LookupId")
            .contains("LookupPubKey");
  }

  @Test
  void getPublicKeyByID(BpmClient bpmClient) {
    BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
    String threemaId = "validId";
    ReceiverData recDatId = new ReceiverData();
    recDatId.setIdentifier(threemaId);
    recDatId.setType(LookupType.THREEMAID);
    ExecutionResult resultId = bpmClient.start().subProcess(callable).execute(recDatId);
    ReceiverData resultDataId = resultId.data().last();
    History historyId = resultId.history();
    assertThat(resultDataId.getApiResponse()).contains("200");
    assertThat(resultDataId.getPublicKey())
            .isEqualTo("ffbb40cfced42f75c4d83c7d35300c0698bf3ef1ab49ace323a1bbc38ee23f36");
    assertThat(historyId.elementNames()).contains("call(receiverData)")
            .contains("LookupPubKey")
            .doesNotContain("LookupId");
  }

  @Test
  void getPublicKeyByInvalidID(BpmClient bpmClient) {
    BpmElement callable = RECEIVER_INFO_PROCESS.elementName("call(receiverData)");
    String threemaId = "invalidID";
    ReceiverData recDatId = new ReceiverData();
    recDatId.setIdentifier(threemaId);
    recDatId.setType(LookupType.THREEMAID);
    ExecutionResult resultId = bpmClient.start().subProcess(callable).execute(recDatId);
    ReceiverData resultDataId = resultId.data().last();
    History historyId = resultId.history();
    assertThat(resultDataId.getApiResponse()).contains("404");
    assertThat(historyId.elementNames()).contains("call(receiverData)")
            .contains("LookupPubKey")
            .doesNotContain("LookupId");
  }
}
