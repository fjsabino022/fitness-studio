# FITNESS MICROSERVICE

## Features
1. I can fetch a list of products
2. I can fetch a single product
3. I can buy a single product
4. I want to fetch the following informations related to my subscription (e.g. start date, end date, duration of the subscription, prices, tax)
5. I can pause and unpause my subscription
6. I can cancel my active subscription

## Next Steps
1. Liquibase for database
2. Client authentication/authorization (OAuth2 - Spring Security)
3. Optional Story 1
4. Optional Story 2

## Technologies
1. Java
2. Spring Boot/Spring
3. Junit/Mockito
4. Cucumber
5. Restdocs

## Run local environment

##### 1. Clone project
git clone https://github.com/fjsabino022/fitness-studio.git (Default branch: main)

##### 2. Unit Tests
mvn clean test 

##### 3. Acceptance Tests
mvn test -Pacceptance
  
##### 4. Integration Tests (Documentation generation)
mvn test -Pintegration

##### 5. Package and Generation main document
mvn package 

##### 6. Start Microservice
mvn spring-boot:run

## Documentation
@See /api.html or after executing steps 4 and 5, /target/generated-docs/api.html