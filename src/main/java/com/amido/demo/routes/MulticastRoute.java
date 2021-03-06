package com.amido.demo.routes;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MulticastRoute extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("direct:multicast-route")
        .multicast()
        .to("{{my-app.my-to-endpoint}}")
        .to("file:{{my-app.test-root-folder}}/camel-multicast");
  }
}
