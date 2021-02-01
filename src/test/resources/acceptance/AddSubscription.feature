#Author: fsabino
Feature: Add subscription
  As a User, I want to add a subscription

  Scenario: Add a subscription
    Given I am a User
    And I have "AA1" as product code
    And I have "SUBABSYEAR" as subscription code
    When I want to add a subscription
    Then The subscription is created
    And I get the subscription code
    And I cancel my subscription

  Scenario Outline: Error creating the subscription ("<description>")
    Given I am a User
    And I have "<code>" as product code
    And I have "<subscriptionCode>" as subscription code
    When I want to add a subscription
    Then I get the http status "<httpStatus>"
    And The message is not empty

    Examples: 
      | code    | subscriptionCode | httpStatus            | description                          |
      |         | SUBABSYEAR       | UNPROCESSABLE_ENTITY  | Product code empty                   |
      | AA1     |                  | UNPROCESSABLE_ENTITY  | Subscription code empty              |
      | AA1     |          9999999 | INTERNAL_SERVER_ERROR | Subscription code invalid            |
      | 9999999 | SUBABSYEAR       | INTERNAL_SERVER_ERROR | Product code invalid                 |
      | AA1     | SUBMUSCLEYEAR    | INTERNAL_SERVER_ERROR | Subscription code of another product |
