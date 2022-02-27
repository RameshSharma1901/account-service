# Spring Boot "Microservice" account-service Project

This is a  Java / Maven / Spring Boot (version 2.6.3) application to demonstrate rest APIs implementation both as a producer and consumer to validate account info.

## How to Run 

This application is packaged as a jar which gets deployed on embedded Tomcat server. No Tomcat or JBoss installation is necessary. 

* Clone this repository 
* Make sure you are using JDK 13 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service using below maven command:
```
        mvn spring-boot:run -Dspring-boot.run.profiles=default     
```

Once the application runs you should see something like this

```
2022-02-27 00:17:43.161  INFO 7248 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 8080 (http) with context path ''
2022-02-27 00:17:43.177  INFO 7248 --- [           main] c.j.p.r.RestServiceApplication           : Started RestServiceApplication in 3.265 seconds (JVM running for 3.753)
```

## About the Service

The service exposes rest API which is used to validate account number with given source systems. Source systems in turn are another external APIs whose URLs are maintained by the service. The service then combines the results of external source APIs and returns back reponse to consumer.


### Ex. To validate account A123456 against source systems source1 and source2, the request and response should be in below format

Request:

{
  "accountNumber": "A123456" ,
  "sources": [
    "source1" ,
    "source2"
  ]
}

Reponse:

{"result":[{"source":"source1","isValid":true},{"source":"source2","isValid":false}]}


### The source system API urls are stored in application.yml file in below format

providers:
  - name: source1
    url: https://source1.com/v1/api/account/validate
  - name: source2
    url: https://source2.com/v1/api/account/validate

Sample request and response format for source1 system should be in below format

Request: 
{"accountNumber":"A123456"}

Reponse: 
{"source":"source1","isValid":true}

## Integration Tests
Application has got Springboot integration tests to demonstrate the complete work flow.
