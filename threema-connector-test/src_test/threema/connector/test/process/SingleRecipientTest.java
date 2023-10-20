package threema.connector.test.process;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.threema.mocks.ThreemaServiceMock;
import threema.connector.ReceiverData;

@IvyProcessTest(enableWebServer = true)
public class SingleRecipientTest {

  private static final BpmProcess SINGLE_RECEIVER_PROCESS = BpmProcess.name("singleRecipient");
  private static final String VALID_ID = "validId";
  private static final String INVALID_EMAIL = "invalid@mail.ch";
  private static final String INVALID_PHONE = "41000000000";
  private static final String MESSAGE = "Hello World";
  private static final String PRIVATE_KEY = "ff364c727068fd6e3e6a711918393fa37649d902402a8eb31af108e79f625d82";

  @BeforeEach
  void setup(AppFixture fixture) {
    Ivy.var().set("threemaConnector.privateKey", PRIVATE_KEY);
    Ivy.var().set("threemaConnector.secret", "secret");
    Ivy.var().set("threemaConnector.threemaId", "threemaId");
    fixture.config("RestClients.ThreemaGateway.Url", ThreemaServiceMock.URI);
  }

  @Test
  void sendMessageToValidSingleRecipientById(BpmClient bpmClient) {
    BpmElement callable = SINGLE_RECEIVER_PROCESS.elementName("call(String,String,LookupType)");
    ExecutionResult result = bpmClient.start().subProcess(callable).execute(MESSAGE, VALID_ID,
            util.LookupType.THREEMAID);
    ReceiverData resultData = result.data().last();
    String apiStatus = resultData.getApiResponse();
    assertThat(apiStatus).contains("200");
  }

  @Test
  void sendMessageToInvalidSingleRecipientByEmail(BpmClient bpmClient) {
    BpmElement callable = SINGLE_RECEIVER_PROCESS.elementName("call(String,String,LookupType)");
    ExecutionResult result = bpmClient.start().subProcess(callable).execute(MESSAGE, INVALID_EMAIL,
            util.LookupType.EMAIL);
    ReceiverData resultData = result.data().last();
    String apiStatus = resultData.getApiResponse();
    assertThat(apiStatus).isEqualTo("ID-Lookup: 404");
  }

  @Test
  void sendMessageToInvalidSingleRecipientByPhone(BpmClient bpmClient) {
    BpmElement callable = SINGLE_RECEIVER_PROCESS.elementName("call(String,String,LookupType)");
    ExecutionResult result = bpmClient.start().subProcess(callable).execute(MESSAGE, INVALID_PHONE,
            util.LookupType.PHONE);
    ReceiverData resultData = result.data().last();
    String apiStatus = resultData.getApiResponse();
    assertThat(apiStatus).isEqualTo("ID-Lookup: 404");
  }
}
