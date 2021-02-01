#Author: fsabino
Feature: Get subscription
  As a User, I want to get my active subscriptions

  Background: Add my subscriptions
    Given I am a User
    And I have "AA1" as product code
    And I have "SUBABSMONTH" as subscription code
    When I want to add a subscription
    Then The subscription is created
    And I have "AA2" as product code
    And I have "SUBYOGAYEAR" as subscription code
    When I want to add a subscription
    Then The subscription is created

  Scenario: Get my subscriptions
    Given I am a User
    When I want to get my subscriptions
    Then I get my subscriptions
    And They are active subscriptions
    And They are mine
