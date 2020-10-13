package au.com.dius.pactworkshop.consumer;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.hamcrest.beans.HasProperty;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasProperty;;

public class ClientTest {

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8089);

  @Test
  public void canProcessTheJsonPayloadFromTheProvider() throws UnirestException {

    String body = "{\"bids\":[{\"imp_id\":\"0\"}]}";
    stubFor(post(urlPathEqualTo("/provider.json"))
      .withQueryParam("lib", matching(".+"))
      .willReturn(aResponse()
        .withStatus(200)
        .withHeader("Content-Type", "application/json")
        .withBody(body)));

    List<Object> data = new Client("http://localhost:8089").fetchAndProcessData(LocalDateTime.now());

    assertThat(data, hasSize(1));
    // assertThat(data.get(0), hasProperty("imp_id"));
  }

}
