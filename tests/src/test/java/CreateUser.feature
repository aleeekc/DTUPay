#Author Aleksandar, Kasper

Feature: A user tries to sign up with DTU PAY

  Scenario: An already existing user tries to sign up again.
    Given The user is registered with Fast Money Bank
    And The user already registered with DTU PAY
    When The user tries to register with DTU PAY
    Then User creation fails


  Scenario: A new user, who has a bank account tries to sign up.
    Given The user not registered with DTU PAY
    And The user is registered with Fast Money Bank
    When The user tries to register with DTU PAY
    Then User creation succeeds


  Scenario: A new user who does not have an account with the bank tries to sign up.
    Given The user not registered with DTU PAY
    And The user is not registered with Fast Money Bank
    When The user tries to register with DTU PAY
    Then User creation fails




