Feature: New Phone
  Delivery of new Phone

  Scenario: Delivery of Phone
    Given Delivery of 1 phone
    When Phone has been delivered
    Then Quantity of phones has been increased by 1
