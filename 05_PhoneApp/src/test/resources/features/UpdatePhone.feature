Feature: Change phone serial number
  Change serial number phone after replacement

  Scenario: Phone after replacement change their serial number
    Given Phone "Alcatel"
    When Phone serial number should be updated to 5151
    Then Phone have a new serial number
