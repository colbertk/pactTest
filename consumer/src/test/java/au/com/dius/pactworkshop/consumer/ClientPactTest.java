package au.com.dius.pactworkshop.consumer;

import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit.PactProviderRule;
import au.com.dius.pact.consumer.junit.PactVerification;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ClientPactTest {

  // This sets up a mock server that pretends to be The Exchange
  @Rule
  public PactProviderRule provider = new PactProviderRule("The Exchange", this);

  private LocalDateTime dateTime;
  private String dateResult;

  // This defines the expected interaction for out test
  @Pact(provider = "The Exchange", consumer = "Our Consumer")
  public RequestResponsePact pact(PactDslWithProvider builder) {
    dateTime = LocalDateTime.now();
    dateResult = "2013-08-16T15:31:20+10:00";
    return builder
      .given("data count > 0")
      .uponReceiving("a request for json data")
      .path("/provider.json")
      .method("POST")
      .query("lib=" + "prebid")
      .willRespondWith()
      .status(200)
      .body( "{\"bids\":[{\"imp_id\":\"0\"}]}")
      .toPact();
  }

  @Test
  @PactVerification("The Exchange")
  public void pactWithOurProvider() throws UnirestException {
    // Set up our HTTP client class
    SupplierClient client = new SupplierClient(provider.getUrl());

    // Invoke out client
    List<Object> result = client.fetchAndProcessData(dateTime);

    assertThat(result, hasSize(1));
    // assertThat(result.get(0), is(1));
    // assertThat(result.get(1), is(ZonedDateTime.parse(dateResult)));
  }
}
