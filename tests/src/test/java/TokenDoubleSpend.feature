#Author: James
Feature: Token should not be able to used twice.

  Scenario: A merchant tries to use the same token twice when submitting a transaction.
    Given A registered user with balance 5000 sends a token to a merchant to pay
    And The merchant submits the transaction for 30 and it succeeds
    When The merchant submits the transaction for 30 again
    Then The second transaction for 30 fails