package au.com.dius.pactworkshop.consumer;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

public class Client {

  private final String url;

  public Client(String url) {
    this.url = url;
  }

  private JSONObject loadProviderJson(LocalDateTime dateTime) throws UnirestException {
    // return Unirest.get(url + "/provider.json")
    // .queryString("validDate", dateTime.toString())
    // .asJson().getBody();
    com.mashape.unirest.http.HttpResponse<String> response = Unirest.post(url + "/provider.json")
    .queryString("lib", "prebid")
    .body(
        "{\n    \"imp\": [\n        {\n            \"id\": 0,\n            \"tagid\": \"AP_Desktop_300X600\",\n            \"floor\": 0.01,\n            \"banner\": {\n                \"format\": [\n                    {\n                        \"w\": 300,\n                        \"h\": 250\n                    },\n                    {\n                        \"w\": 300,\n                        \"h\": 600\n                    }\n                ]\n            }\n        }\n    ]\n}")
        .asString();
    JSONObject jsonObject = new JSONObject(response);
    return jsonObject;
  }

  public List<Object> fetchAndProcessData(LocalDateTime dateTime) throws UnirestException {
    JSONObject data = loadProviderJson(dateTime);
    System.out.println("data=" + data);

    JSONObject jsonObject = data;
    System.out.println(data);
    // int value = 100 / jsonObject.getInt("count");
    JSONObject body = new JSONObject(jsonObject.getString("body"));
    System.out.println("body=" + body);
    JSONArray bids = body.getJSONArray("bids");
    // ZonedDateTime date = ZonedDateTime.parse(jsonObject.getString("date"));

    // System.out.println("value="  + value);
    // System.out.println("date=" + date);
    return Arrays.asList(bids);
  }
}
