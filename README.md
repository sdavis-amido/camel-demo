# Spring Boot Camel Demo

## Synposis

The purpose of this small project is to show the following:

* Camel interaction with Spring Boot
* Publishing messages to queues (SQS & Kafka)
* Consuming messages from queues (SQS & Kafka)
* Spring/JUnit test integration with Camel

Multiple scenarios are demonstrated, you just need to uncomment the relevant "from" and "to"
endpoint names in application.yml

## Prerequisites

* AWS account access
* AWS SQS queue ARN
* Kafka broker available (see docker compose further down)

## Setting up

* Set the following environment variables in the environment
    * AWS_KEY
    * AWS_SECRET

## Starting the app

```bash
mcn clean spring-boot:run
```

## Pushing Messages onto the queue

```bash
curl -H "Content-Type: application/json" localhost:8080/message/send -d 'My message to be sent...'
```

## Kafka Docker compose

The included docker compose can be used for this example.

Execute the commands:

```bash
docker network create amido

docker compose -f docker-compose.yml up -d
```