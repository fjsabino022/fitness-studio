#Author: fsabino
Feature: Get Products
  As a User, I want to get the list of all products with their subscriptions

  Scenario: Get all products
    Given I am a User
    When I want to get all products
    Then The list of products is returned
    And The number of product is "3"
    And All products have at least one subscription
