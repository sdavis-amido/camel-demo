package com.amido.demo.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class MessageService {

  @Autowired private KafkaTemplate<String, Object> kafkaTemplate;

  @Value(value = "${kafka.topicName:camel-demo-topic}")
  private String topicName;

  @Value(value = "${kafka.groupId:the-group-id}")
  private String groupId;

  public String send(String message) {

    log.debug("Using topic name : {}", topicName);

    ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicName, message);

    future.addCallback(
        new ListenableFutureCallback<>() {

          @Override
          public void onSuccess(SendResult<String, Object> result) {
            log.debug(
                "Sent message=["
                    + message
                    + "] with offset=["
                    + result.getRecordMetadata().offset()
                    + "]");
          }

          @Override
          public void onFailure(Throwable ex) {
            log.debug("Unable to send message=[" + message + "] due to : " + ex.getMessage());
          }
        });

    return "ACK " + getMemory();
  }

  @KafkaListener(
      topics = "#{'${kafka.topicName:camel-demo-topic}'.split(',')}",
      groupId = "#{'${kafka.groupId:the-group-id}'}")
  public void listen(Object message) {

    ConsumerRecord<String, Object> consumerRecord = (ConsumerRecord<String, Object>) message;

    log.debug("Received Message in group {} : {}", groupId, consumerRecord.value());
  }

  private String getMemory() {

    System.gc();
    System.gc();

    Runtime rt = Runtime.getRuntime();
    long totalMem = rt.totalMemory();
    long freeMem = rt.freeMemory();
    long usedMem = totalMem - freeMem;

    return "" + (usedMem / 1048576) + " MB";
  }
}
