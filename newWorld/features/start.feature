# Created by Yp_Love at 2019/8/1 0001
Feature: PPAP
  # Enter feature description here

  Scenario: ApplePen
    Given I have Pen
    When I have Apple
    Then Huh ApplePen

  Scenario: PineApplePen
    Given I have Pen
    When I have PineApple
    Then Huh PineApplePen

  Scenario: PineApplePenApplePen
    Given I have ApplePen
    When I have PineApplePen
    Then Huh PineApplePenApplePen