package com.amido.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AwsSqsClientConfig {

  @Value("${cloud.aws.credentials.accessKey}")
  private String awsId;

  @Value("${cloud.aws.credentials.secretKey}")
  private String awsKey;

  @Value("${cloud.aws.region.static}")
  private String region;

  @Bean
  public SqsClient sqsclient() {

    AwsBasicCredentials awsCreds = AwsBasicCredentials.create(awsId, awsKey);

    return SqsClient.builder()
        .region(Region.of(region))
        .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
        .build();
  }
}
