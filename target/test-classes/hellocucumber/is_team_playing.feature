# Created by nikhi at 31/12/2020
Feature: Check if Football Team is playing
  # Enter feature description here

  Scenario Outline: Given date and team, check if team is playing
    Given Initialize Driver
    Given team is "<team>"
    Given date is "<date>"
    When I navigate to PL Fixtures
    Then I verify I am on PL Fixtures page
    When I load the fixtures for "<date>"
    Then I verify "<team>" playing is: "<isPlaying>"


    Examples:
      | team      | date          | isPlaying
      | Liverpool | 14/01/2021    | False
      | Arsenal   | 14/01/2021    | True


