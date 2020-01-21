Feature: Belly

  @failed @C1
  Scenario: a few cukes failed
    Given I have 42 cukes in my belly

  @passed @C2
  Scenario: a few cukes passed
    Given I have 42 cukes in my belly

  @retest @C3
  Scenario: a few cukes retest
    Given I have 42 cukes in my belly
