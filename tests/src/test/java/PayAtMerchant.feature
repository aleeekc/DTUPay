#Author: Kasper, Kim
Feature: A user should be able to pay at a merchant, to pay a user must present a token.


  Scenario: A user with a balance of $10 wants to purchase an item which they can afford from a valid merchant using an issued token.
    Given A registered user with a balance of 10
    And An issued token
    And A registered merchant with a valid bank account
    When The user purchases an item costing 5
    Then Money should be transferred from the user to the merchant


  Scenario: A user with a balance of $10 wants to purchase an item which they can afford from a valid merchant but the user has run out of tokens.
    Given A registered user with a balance of 10
    And A registered merchant with a valid bank account
    When The user purchases an item costing 5
    Then Money should not be transferred from the user to the merchant


  Scenario: A user with a balance of $10 wants to purchase an item which they cannot afford from a valid merchant.
    Given A registered user with a balance of 10
    And An issued token
    And A registered merchant with a valid bank account
    When The user purchases an item costing 50
    Then Money should not be transferred from the user to the merchant



  Scenario: A user tries to purchase an item with a merchant not registered with DTU PAY
    Given A registered user with a balance of 10
    And A merchant registered with DTU PAY
    But The merchant is no longer with DTU PAY
    When The user purchases an item costing 5
    Then Money should not be transferred from the user to the merchant


  Scenario: A user tries to purchase an item with a merchant registered with dtu pay but not registered with the bank.
    Given A registered user with a balance of 10
    And A merchant registered with DTU PAY
    But The merchant does not have a bank account
    When The user purchases an item costing 5
    Then Money should not be transferred from the user to the merchant


# Use a token twice!