#Author: fsabino
Feature: Pause subscription
  As a User, I want to pause my active subscriptions

  Background: Add a subscription
    Given I am a User
    And I have "AA3" as product code
    And I have "SUBMUSCLEYEAR" as subscription code
    When I want to add a subscription
    And I get the subscription code

  Scenario: Pause my subscription
    Given I am a User
    When I want to pause my subscription
    Then My subscription is not active
