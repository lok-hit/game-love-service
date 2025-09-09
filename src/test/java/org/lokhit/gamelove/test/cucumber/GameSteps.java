package org.lokhit.gamelove.test.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.lokhit.gamelove.dto.CreateGameDto;
import org.lokhit.gamelove.dto.GameDto;
import org.lokhit.gamelove.entity.Game;
import org.lokhit.gamelove.service.GameService;
import org.lokhit.gamelove.service.LoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GameSteps {

    @Autowired
    private GameService gameService;

    @Autowired
    private LoveService loveService;

    private List<Game> gameList;
    private List<GameDto> topGames;
    private Exception creationException;

    @When("I create a game named {string}")
    public void i_create_a_game_named(String gameName) {
        try {
            gameService.createGame(new CreateGameDto(gameName));
            creationException = null;
        } catch (Exception e) {
            creationException = e;
        }
    }

    @Then("the game {string} should exist in the system")
    public void the_game_should_exist(String gameName) {
        gameList = gameService.getAllGames();
        assertTrue(gameList.stream().anyMatch(g -> g.getGameName().equalsIgnoreCase(gameName)));
    }

    @Given("the game {string} exists")
    public void the_game_exists(String gameName) {
        gameService.createGame(new CreateGameDto(gameName));
    }

    @When("I try to create a game named {string}")
    public void i_try_to_create_a_game_named(String gameName) {
        try {
            gameService.createGame(new CreateGameDto(gameName));
            creationException = null;
        } catch (Exception e) {
            creationException = e;
        }
    }

    @Then("I should receive an error saying {string}")
    public void i_should_receive_an_error_saying(String expectedMessage) {
        assertNotNull(creationException);
        assertTrue(creationException.getMessage().contains(expectedMessage));
    }

    @When("I request the list of all games")
    public void i_request_the_list_of_all_games() {
        gameList = gameService.getAllGames();
    }

    @Then("the list should contain {string} and {string}")
    public void the_list_should_contain_two_games(String game1, String game2) {
        assertTrue(gameList.stream().anyMatch(g -> g.getGameName().equalsIgnoreCase(game1)));
        assertTrue(gameList.stream().anyMatch(g -> g.getGameName().equalsIgnoreCase(game2)));
    }

    @When("I request the top {int} loved games")
    public void i_request_top_loved_games(int limit) {
        topGames = loveService.getTopLovedGames(limit);
    }

    @Then("the first game in the ranking should be {string}")
    public void the_first_game_in_ranking_should_be(String expectedGameName) {
        assertFalse(topGames.isEmpty());
        assertEquals(expectedGameName, topGames.get(0).gameName());
    }
}


