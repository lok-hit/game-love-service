package controller;

import dto.CreatePlayerDto;
import dto.PlayerDto;
import dto.UpdatePlayerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import service.PlayerService;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @Operation(summary = "Create a new player")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Player created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody CreatePlayerDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.createPlayer(dto));
    }

    @Operation(summary = "Update an existing player")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Player updated"),
            @ApiResponse(responseCode = "404", description = "Player not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(@PathVariable Long id, @Valid @RequestBody UpdatePlayerDto dto) {
        return ResponseEntity.ok(playerService.updatePlayer(id, dto));
    }

    @Operation(summary = "Delete a player")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Player deleted"),
            @ApiResponse(responseCode = "404", description = "Player not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get player by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Player found"),
            @ApiResponse(responseCode = "404", description = "Player not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayerById(id));
    }

    @Operation(summary = "Search players by keyword")
    @ApiResponse(responseCode = "200", description = "Search results")
    @GetMapping("/search")
    public ResponseEntity<List<PlayerDto>> searchPlayers(@RequestParam String keyword) {
        return ResponseEntity.ok(playerService.searchPlayers(keyword));
    }

    @Operation(summary = "Get paginated list of players")
    @ApiResponse(responseCode = "200", description = "Paged players returned")
    @GetMapping
    public ResponseEntity<List<PlayerDto>> getPagedPlayers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username") String sortBy) {
        return ResponseEntity.ok(playerService.getPlayersPaged(page, size, sortBy).getContent());
    }
}


