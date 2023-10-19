package threema.connector.test.process;

import static org.assertj.core.api.Assertions.assertThat;

import javax.xml.bind.DatatypeConverter;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ch.ivyteam.ivy.bpm.engine.client.BpmClient;
import ch.ivyteam.ivy.bpm.engine.client.ExecutionResult;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmElement;
import ch.ivyteam.ivy.bpm.engine.client.element.BpmProcess;
import ch.ivyteam.ivy.bpm.engine.client.sub.SubRequestBuilder;
import ch.ivyteam.ivy.bpm.exec.client.IvyProcessTest;
import ch.threema.apitool.CryptTool;
import ch.threema.apitool.results.EncryptResult;
import threema.connector.receiverData;
import ch.ivyteam.ivy.environment.AppFixture;
import ch.ivyteam.ivy.environment.Ivy;
import ch.ivyteam.threema.mocks.ThreemaServiceMock;

@IvyProcessTest(enableWebServer = true)
public class SendMessageTest {

	private static final BpmProcess SEND_MESSAGE_PROCESS = BpmProcess.name("sendMessage");
	
	private static final String ECHO_PUBLIC_KEY = "4a6a1b34dcef15d43cb74de2fd36091be99fbbaf126d099d47d83d919712c72b";
	private static final String PRIVATE_KEY = "ff364c727068fd6e3e6a711918393fa37649d902402a8eb31af108e79f625d82";
	private static final String INVALID_PUBLIC_KEY = "0000004dcef15d43cb74de2fd36091be99fbbaf126d099d47d83d919712c72b";
	private static final String VALID_ID = "validId";
	private static final String INVALID_ID = "invalidId";
	private static final String message = "Hello World";
	private static String encryptedMessage;
	private static String nonce;
	
	
	@BeforeAll
	static void encryptMessage() {
		EncryptResult encryptResult = CryptTool.encryptTextMessage(
				message, 
				DatatypeConverter.parseHexBinary(PRIVATE_KEY), 
				DatatypeConverter.parseHexBinary(ECHO_PUBLIC_KEY)
				);
		encryptedMessage = DatatypeConverter.printHexBinary(encryptResult.getResult());
		nonce = DatatypeConverter.printHexBinary(encryptResult.getNonce());
	}
	
	@BeforeEach
	void setup(AppFixture fixture) {
		fixture.config("RestClients.ThreemaGateway.Url", ThreemaServiceMock.URI);
	}
	
	@Test
	void sendValidMessage(BpmClient bpmClient) {
		BpmElement callable = SEND_MESSAGE_PROCESS.elementName("call(receiverData)");
		
		receiverData recDat = new receiverData();
		recDat.setThreemaId(VALID_ID);
		recDat.setPublicKey(ECHO_PUBLIC_KEY);
		recDat.setEncryptedMessage(encryptedMessage);
		recDat.setNonce(nonce);
		recDat.setApiResponse("");
		
		ExecutionResult result = bpmClient.start().subProcess(callable).execute(recDat);
		receiverData resultData = result.data().last();
		
		assertThat(resultData.getApiResponse()).contains("200");
	}
	
	@Test
	void sendInvalidMessage(BpmClient bpmClient) {
		BpmElement callable = SEND_MESSAGE_PROCESS.elementName("call(receiverData)");
		
		receiverData recDat = new receiverData();
		recDat.setThreemaId(INVALID_ID);
		recDat.setPublicKey(INVALID_PUBLIC_KEY);
		recDat.setEncryptedMessage(encryptedMessage);
		recDat.setNonce(nonce);
		recDat.setApiResponse("");
		
		ExecutionResult result = bpmClient.start().subProcess(callable).execute(recDat);
		receiverData resultData = result.data().last();
		
		assertThat(resultData.getApiResponse()).contains("404");
		
	}
	
}
