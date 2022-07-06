package com.amido.demo.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageLoggingRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {

    from("direct:logging-route")
        .process(
            exchange -> {
              String payload = exchange.getIn().getBody(String.class);
              log.debug("SENDING PAYLOAD = {} (but uppercase-ing it)", payload);
              exchange.getIn().setBody(payload.toUpperCase());
            })
        .to("{{my-app.my-to-endpoint}}");
  }
}
