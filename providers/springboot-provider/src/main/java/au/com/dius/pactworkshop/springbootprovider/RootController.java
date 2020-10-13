package au.com.dius.pactworkshop.springbootprovider;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class RootController {

  @RequestMapping("/provider.json")
  public Map<String, Serializable> providerJson(@RequestParam(required = false) String validDate) {
    // LocalDateTime validTime = LocalDateTime.parse(validDate);
    Map<String, Serializable> map = new HashMap<>(1);
    // map.put("imp_id", 0);
    // map.put("validDate", LocalDateTime.now().toString());
    // map.put("count", 1000);
    ObjectMapper mapper = new ObjectMapper();
    ArrayNode bid = mapper.createArrayNode();
    ObjectNode imp = mapper.createObjectNode();
    imp.put("imp_id", "0");
    bid.add(imp);
    map.put("bids", bid);
    // String json = "";
    // try {
    //  json = mapper.writeValueAsString(bid);
    // } catch (JsonProcessingException e) {
    //   // TODO Auto-generated catch block
    //   e.printStackTrace();
    // }
    // return json;
    return map;
  }

}
