package com.amido.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DebuggingInitializer implements ApplicationListener<ApplicationReadyEvent> {

  @Value("${my-app.my-from-endpoint}")
  private String fromEndpoint;

  @Value("${my-app.my-to-endpoint}")
  private String toEndpoint;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    log.debug("\n\nCurrent Config:\n  From: {}\n  To:   {}\n\n", fromEndpoint, toEndpoint);
  }
}
