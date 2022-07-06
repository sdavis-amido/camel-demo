package com.amido.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.CamelContext;
import org.apache.camel.Consume;
import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MessageService {

  @Autowired private CamelContext camelContext;

  public String send(String message) {

    String ret;

    try (ProducerTemplate producerTemplate = camelContext.createProducerTemplate()) {

      // producerTemplate.sendBody("{{my-app.my-endpoint}}", message);
      producerTemplate.sendBody("direct:logging-route", message);
      // producerTemplate.sendBody("direct:multicast-route", message);

      ret = "ACK";

    } catch (Exception e) {

      log.error(e.getMessage());
      ret = "NACK";
    }

    return ret;
  }

  @Consume("{{my-app.my-from-endpoint}}")
  public void receive(@Body String payload) {
    log.debug("RECEIVED FROM QUEUE : {}", payload);
  }
}