package controller;

import dto.CreateLoveDto;
import dto.LoveDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.LoveService;

import java.util.List;

@RestController
@RequestMapping("/api/loves")
public class LoveController {

    private final LoveService loveService;

    public LoveController(LoveService loveService) {
        this.loveService = loveService;
    }

    @Operation(summary = "Create a new love relation")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Love created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<LoveDto> createLove(@Valid @RequestBody CreateLoveDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loveService.createLove(dto));
    }

    @Operation(summary = "Delete a love relation")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Love deleted"),
            @ApiResponse(responseCode = "404", description = "Love not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLove(@PathVariable Long id) {
        loveService.deleteLove(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get love relation by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Love found"),
            @ApiResponse(responseCode = "404", description = "Love not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LoveDto> getLove(@PathVariable Long id) {
        return ResponseEntity.ok(loveService.getLoveById(id));
    }

    @Operation(summary = "Search loves by player and game")
    @ApiResponse(responseCode = "200", description = "Search results")
    @GetMapping("/search")
    public ResponseEntity<List<LoveDto>> searchLoves(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String gameName) {
        return ResponseEntity.ok(loveService.searchLoves(username, gameName));
    }

    @Operation(summary = "Get paginated list of loves")
    @ApiResponse(responseCode = "200", description = "Paged loves returned")
    @GetMapping
    public ResponseEntity<List<LoveDto>> getPagedLoves(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lovedAt") String sortBy) {
        return ResponseEntity.ok(loveService.getLovesPaged(page, size, sortBy).getContent());
    }
}


