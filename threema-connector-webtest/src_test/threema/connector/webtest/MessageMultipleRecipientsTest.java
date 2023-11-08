package threema.connector.webtest;

import static com.codeborne.selenide.Condition.empty;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import com.axonivy.ivy.webtest.IvyWebTest;
import com.axonivy.ivy.webtest.engine.EngineUrl;
import com.axonivy.ivy.webtest.engine.WebAppFixture;
import com.codeborne.selenide.ElementsCollection;

import ch.ivyteam.threema.mocks.ThreemaServiceMock;

@IvyWebTest
public class MessageMultipleRecipientsTest {

  @AfterEach
  public void cleanFixture(WebAppFixture fixture) {
    fixture.resetConfig("RestClients.ThreemaGateway.Url");
  }

  @Test
  public void sendMessage(WebAppFixture fixture) {
    String mockUrl = ThreemaServiceMock.URI.replaceAll("\\{", "%7B").replaceAll("\\}", "%7D").replaceAll("/", "%2F");
    fixture.config("RestClients.ThreemaGateway.Url", mockUrl);
    open(EngineUrl.createProcessUrl("threema-connector-demo/18B8EEA3B9A84FAE/SendMessageToMultipleRecipients.ivp"));
    String message = "Hello World";
    String recipients = "validId\ninvalidId";
    // Assert empty form
    $(By.id("form:sendDemoMessageDataPlainMessage")).shouldBe(empty);
    $(By.id("form:sendDemoMessageDataReceiver")).shouldBe(empty);
    // Proceed without required fields
    $(By.id("form:proceed")).click();
    // Assert all fields required
    ElementsCollection errorMessages = $$(By.cssSelector(".ui-state-error"));
    assertThat(errorMessages).hasSize(4);
    // Fill out form
    $(By.id("form:sendDemoMessageDataPlainMessage")).sendKeys(message);
    $(By.id("form:sendDemoMessageDataReceiver")).sendKeys(recipients);
    // Assert filled out form
    $(By.id("form:sendDemoMessageDataPlainMessage")).shouldBe(value(message));
    $(By.id("form:sendDemoMessageDataReceiver")).shouldBe(value(recipients));
  }
}
