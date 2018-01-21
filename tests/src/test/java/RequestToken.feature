#Authors: Kasper, Jesper
Feature: Requesting tokens as a user.

  Scenario: Requesting a token as a registered user should give a valid token.
    Given A user who is already registered
    When The user requests a new token using it's UUID
    Then The user should get a valid token UUID


    Scenario: Requesting a token as a non registered user should not give a valid token.
      Given A user who is not already registered
      When The user requests a new token using it's UUID
      Then The user should not get a valid token UUID
