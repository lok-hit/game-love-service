Feature: Player creation

  Scenario: Create a new player successfully
    Given no player exists with username "mateusz"
    When I create a player with username "mateusz"
    Then the player should be created

  Scenario: Fail to create duplicate player
    Given a player exists with username "mateusz"
    When I try to create another player with username "mateusz"
    Then a validation exception should be thrown