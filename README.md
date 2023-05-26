# activemq-poc

### Requirements
*Note: It was run with the versions below, will probably work with other minor versions
- Java 11.0.9.101
- Maven 3.9.0
- Docker if ran with a local message broker

### Running locally
src/main/resources/application.yml

spring.activemq.broker-url: "tcp://localhost:61616"

#### Commands:
- docker-compose -f docker-compose.yml up --build -d
- mvn spring-boot:run

ActiveMQ console will be available at http://localhost:8161/


### Running with aws-hosted broker
src/main/resources/application.yml

spring.activemq.broker-url: "ssl://b-4c49c754-b8f9-4ec4-8fa7-7747d9b65a8f-1.mq.eu-west-1.amazonaws.com:61617"

#### Commands:
- mvn spring-boot:run

ActiveMQ console will be available at https://b-4c49c754-b8f9-4ec4-8fa7-7747d9b65a8f-1.mq.eu-west-1.amazonaws.com:8162/
Console credentials are the same ones as in the project properties.