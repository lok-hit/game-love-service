Feature: Game Management

  Scenario: Create a new game
    When I create a game named "The Witcher 3"
    Then the game "The Witcher 3" should exist in the system

  Scenario: Prevent duplicate game creation
    Given the game "Cyberpunk 2077" exists
    When I try to create a game named "Cyberpunk 2077"
    Then I should receive an error saying "Game already exists"

  Scenario: List all games
    Given the game "FIFA 25" exists
    And the game "Minecraft" exists
    When I request the list of all games
    Then the list should contain "FIFA 25" and "Minecraft"

  Scenario: Get top loved games
    Given the game "FIFA 25" exists
    And the game "Minecraft" exists
    And the player "ola" likes the game "FIFA 25"
    And the player "mateusz" likes the game "FIFA 25"
    And the player "ola" likes the game "Minecraft"
    When I request the top 2 loved games
    Then the first game in the ranking should be "FIFA 25"

