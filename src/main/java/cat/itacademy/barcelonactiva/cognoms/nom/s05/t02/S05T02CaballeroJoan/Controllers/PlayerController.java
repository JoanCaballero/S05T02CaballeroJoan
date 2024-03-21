package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Controllers;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.DTO.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Exceptions.PlayerAlreadyExistsException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Service.Interfaces.PlayerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @Operation(summary = "Create a new player")
    @PostMapping("/players")
    public ResponseEntity<?> createPlayer(@RequestBody PlayerDTO playerDTO){
        try {
            if (playerDTO.getPlayerName() == null || playerDTO.getPlayerName().isEmpty()) {
                return ResponseEntity.badRequest().body("Player name cannot be empty");
            }
            if (playerDTO.getRegistrationDate() == null) {
                playerDTO.setRegistrationDate(LocalDateTime.now());
            }
            if (playerDTO.getWinRate() == 0.0) {
                playerDTO.setWinRate(0.0);
            }
            PlayerDTO createdPlayer = playerService.save(playerDTO);
            System.out.println("Player created successfully: " + createdPlayer.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
        } catch(PlayerAlreadyExistsException e) {
            System.out.println("Player creation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Already exists a player with this name.");
        } catch(Exception e) {
            System.out.println("Player creation failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the player: " + e.getMessage());
        }
    }

    @Operation(summary = "Update an existing player")
    @PutMapping("/players")
    public ResponseEntity<PlayerDTO> updatePlayer(@RequestBody PlayerDTO playerDTO){
        return ResponseEntity.ok(playerService.update(playerDTO));
    }

    @Operation(summary = "List all existing players with their win rate")
    @GetMapping("/players")
    public ResponseEntity<List<PlayerDTO>> getAllPlayers(){
        return ResponseEntity.ok(playerService.findAll());
    }

    @Operation(summary = "Show the average win rate between all players")
    @GetMapping("/players/ranking")
    public ResponseEntity<Double> getAverageWinRate() {
        return ResponseEntity.ok(playerService.avgWinRate());
    }

    @Operation(summary = "Show a player with the worst win rate")
    @GetMapping("/players/ranking/loser")
    public ResponseEntity<PlayerDTO> getLoser() {
        return ResponseEntity.ok(playerService.loser());
    }

    @Operation(summary = "Show a player with the best win rate")
    @GetMapping("/players/ranking/winner")
    public ResponseEntity<PlayerDTO> getWinner() {
        return ResponseEntity.ok(playerService.winner());
    }
}
