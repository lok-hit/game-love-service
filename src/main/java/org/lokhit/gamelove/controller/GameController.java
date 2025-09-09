package org.lokhit.gamelove.controller;

import org.lokhit.gamelove.dto.CreateGameDto;
import org.lokhit.gamelove.dto.GameDto;
import org.lokhit.gamelove.dto.UpdateGameDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.lokhit.gamelove.service.GameService;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Create a new game")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Game created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/games")
    public ResponseEntity<GameDto> createGame(@Valid @RequestBody CreateGameDto dto) {
        gameService.createGame(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(gameService.createGame(dto));
    }

    @Operation(summary = "Update an existing game")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game updated"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<GameDto> updateGame(@PathVariable Long id, @Valid @RequestBody UpdateGameDto dto) {
        return ResponseEntity.ok(gameService.updateGame(id, dto));
    }

    @Operation(summary = "Get game by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Game found"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGame(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @Operation(summary = "Delete a game")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Game deleted"),
            @ApiResponse(responseCode = "404", description = "Game not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Search games by keyword")
    @ApiResponse(responseCode = "200", description = "Search results")
    @GetMapping("/search")
    public ResponseEntity<List<GameDto>> searchGames(@RequestParam String keyword) {
        return ResponseEntity.ok(gameService.searchGames(keyword));
    }

    @Operation(summary = "Get paginated list of games")
    @ApiResponse(responseCode = "200", description = "Paged games")
    @GetMapping
    public ResponseEntity<List<GameDto>> getPagedGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return ResponseEntity.ok(gameService.getGamesPaged(page, size, sortBy).getContent());
    }

    @GetMapping("/games/sorted-by-loves")
    public Page<GameDto> getGamesSortedByLoves(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return gameService.getGamesSortedByLoveCount(page, size);
    }
}
