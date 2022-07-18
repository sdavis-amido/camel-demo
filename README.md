# Spring Boot Camel Demo

## Synposis

The purpose of this small project is to show the following:

* Camel usage with Spring Boot
* Publishing messages to queues (Azure ServiceBus, SQS & Kafka)
* Consuming messages from queues (Azure ServiceBus, SQS & Kafka)
* Spring/JUnit test integration with Camel

Multiple scenarios are demonstrated, you just need to uncomment the relevant "from" and "to"
endpoint names in application.yml

## Prerequisites

* AWS account access
* AWS SQS queue ARN
* Kafka broker available (see docker compose further down)
* Azure ServiceBus Topic access

## Setting up

* Set the following environment variables in the environment for AWS
    * AWS_KEY
    * AWS_SECRET


* Set the following environment variables in the environment for Azure ServiceBus (uses Stacks
  project topics)
    * SB_ENABLED
    * SB_CONNECTION_STRING
    * SB_SUBSCRIPTION
    * SB_TOPIC

## Starting the app

```bash
mvn clean spring-boot:run
```

## Pushing Messages onto the queue

```bash
curl -H "Content-Type: application/json" localhost:8080/message/send -d '{"name":"steve"}'
```

## Kafka Docker compose

The included docker compose can be used for this example.

Execute the commands:

```bash
docker network create amido

docker compose -f docker-compose.yml up -d
```

## Example Scenarios

The easiest way to exercise the app is to enable in properties (application.yml) one of the
following scenarios:

* Scenario 1 : SQS Queue
* Scenario 2 : SNS Topic to SQS Queue
* Scenario 3 : Kafka Topic
* Scenario 4 : Azure ServiceBus
