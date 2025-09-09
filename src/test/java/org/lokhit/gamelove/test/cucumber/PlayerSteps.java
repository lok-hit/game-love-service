package org.lokhit.gamelove.test.cucumber;

import org.lokhit.gamelove.dto.CreatePlayerDto;
import org.lokhit.gamelove.exception.ValidationException;
import io.cucumber.java.en.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.lokhit.gamelove.service.PlayerService;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PlayerSteps {

    @Autowired
    private PlayerService playerService;

    private Exception capturedException;

    @Given("no player exists with username {string}")
    public void noPlayerExists(String username) {
        try {
            playerService.deletePlayerByUsername(username); // helper method
        } catch (Exception ignored) {}
    }

    @Given("a player exists with username {string}")
    public void playerExists(String username) {
        try {
            playerService.createPlayer(new CreatePlayerDto(username));
        } catch (Exception ignored) {}
    }

    @When("I create a player with username {string}")
    public void iCreatePlayer(String username) {
        try {
            playerService.createPlayer(new CreatePlayerDto(username));
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @When("I try to create another player with username {string}")
    public void iTryCreateDuplicate(String username) {
        try {
            playerService.createPlayer(new CreatePlayerDto(username));
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("the player should be created")
    public void playerShouldBeCreated() {
        assertNull(capturedException);
    }

    @Then("a validation exception should be thrown")
    public void validationExceptionShouldBeThrown() {
        assertNotNull(capturedException);
        assertInstanceOf(ValidationException.class, capturedException);
    }
}
