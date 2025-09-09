package org.lokhit.gamelove.test.cucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.lokhit.gamelove.dto.CreateGameDto;
import org.lokhit.gamelove.dto.GameDto;
import org.lokhit.gamelove.dto.LoveDto;
import org.lokhit.gamelove.service.GameService;
import org.lokhit.gamelove.service.LoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoveSteps {

    @Autowired
    private GameService gameService;

    @Autowired
    private LoveService loveService;

    private String currentUser;
    private List<LoveDto> loveHistory;
    private List<GameDto> topGames;

    @Given("the game {string} exists")
    public void the_game_exists(String gameName) {
        gameService.createGame(new CreateGameDto(gameName));

    }

    @When("the player {string} likes the game {string}")
    public void the_player_likes_the_game(String username, String gameName) {
        currentUser = username;
        loveService.loveGame(username, gameName);
    }

    @When("the player {string} unlikes the game {string}")
    public void the_player_unlikes_the_game(String username, String gameName) {
        loveService.unloveGame(username, gameName);
    }

    @Then("the game {string} should appear in the love history of player {string}")
    public void the_game_should_appear_in_love_history(String gameName, String username) {
        loveHistory = loveService.getLoveHistory(username);
        assertTrue(loveHistory.stream().anyMatch(g -> g.gameName().equalsIgnoreCase(gameName)));
    }

    @Then("the game {string} should not appear in the love history of player {string}")
    public void the_game_should_not_appear_in_love_history(String gameName, String username) {
        loveHistory = loveService.getLoveHistory(username);
        assertFalse(loveHistory.stream().anyMatch(g -> g.gameName().equalsIgnoreCase(gameName)));
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
