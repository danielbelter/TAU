Feature: Is it iPhone ?
  Customer want to know what phone is it

  Scenario Outline: It is iPhone or not
    Given this is "<model>"
    When I ask are you sure I's iPhone
    Then I should be told "<answer>"

    Examples:
      | model     | answer |
      | iPhone    | Yes    |
      | Nokia     | Nope   |
      | Motorolla | Nope   |
