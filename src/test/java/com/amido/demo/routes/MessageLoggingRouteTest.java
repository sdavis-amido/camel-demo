package com.amido.demo.routes;

import com.amido.demo.CamelDemoApplication;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@CamelSpringBootTest
@SpringBootTest(
    classes = {CamelDemoApplication.class},
    properties = {
      "my-app.my-to-endpoint = mock:output",
      "my-app.my-from-endpoint = direct:mock-from",
    })
@MockEndpoints("mock:output")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Slf4j
public class MessageLoggingRouteTest {

  @Autowired private CamelContext camelContext;

  @Autowired private ProducerTemplate producerTemplate;

  @EndpointInject("mock:output")
  private MockEndpoint mockEndpoint;

  @AfterEach
  public void afterEach() {
    mockEndpoint.reset();
  }

  @Test
  public void shouldAutowireProducerTemplate() {
    Assertions.assertNotNull(producerTemplate);
  }

  @Test
  public void shouldInjectEndpoint() throws InterruptedException {

    mockEndpoint.setExpectedMessageCount(1);
    mockEndpoint.expectedBodiesReceived("THIS IS MY MSG");
    mockEndpoint.whenAnyExchangeReceived(
        (processor) -> {
          log.debug(
              "RECEIVED : "
                  + processor.getIn().getHeaders()
                  + ", "
                  + processor.getIn().getBody(String.class));
        });

    producerTemplate.sendBody("direct:logging-route", "this is my msg");

    mockEndpoint.assertIsSatisfied();
  }
}
