#Author: fsabino
Feature: Get Product
  As a User, I want to get a specific product with its subscriptions

  Scenario Outline: Get product
    Given I am a User
    And I have "<code>" as product code
    When I want to get the product
    Then The product is returned
    And The name is "<name>"
    And The description is not empty
    And The product has "<numberSubscriptions>" subcriptions

    Examples: 
      | code | name        | numberSubscriptions |
      | AA1  | ABS CLASSES |                   3 |
      | AA2  | YOGA        |                   1 |

  Scenario: Get product with invalid code
    Given I am a User
    And I have "99999" as product code
    When I want to get the product
    Then The not found response is returned
    And The message is not null
