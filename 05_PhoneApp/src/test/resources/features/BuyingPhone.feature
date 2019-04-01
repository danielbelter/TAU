Feature: BuyingPhone
  Customer buy a phone

  Scenario: Customer buy a phone
    Given Customer choses a phone
    When Customer chose model "Nokia"
    And Customer chose serialnumber 123
    Then Phone has benn sold
