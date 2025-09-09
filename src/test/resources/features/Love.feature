Feature: Love Feature

  Scenario: A player likes a game
    Given the game "The Witcher 3" exists
    When the player "mateusz" likes the game "The Witcher 3"
    Then the game "The Witcher 3" should appear in the love history of player "mateusz"

  Scenario: A player unlikes a game
    Given the game "Cyberpunk 2077" exists
    And the player "mateusz" likes the game "Cyberpunk 2077"
    When the player "mateusz" unlikes the game "Cyberpunk 2077"
    Then the game "Cyberpunk 2077" should not appear in the love history of player "mateusz"

  Scenario: Top loved games ranking
    Given the game "FIFA 25" exists
    And the game "Minecraft" exists
    And the player "ola" likes the game "FIFA 25"
    And the player "mateusz" likes the game "FIFA 25"
    And the player "ola" likes the game "Minecraft"
    When I request the top 2 loved games
    Then the first game in the ranking should be "FIFA 25"