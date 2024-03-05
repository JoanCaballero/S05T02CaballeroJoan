package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Controllers;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.DTO.GameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Service.Interfaces.GameService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
public class GameController {
    @Autowired
    GameService gameService;

    @Operation(summary = "Play a new game and save it")
    @PostMapping("/{id}/games")
    public ResponseEntity<GameDTO> playGame(@PathVariable("id") Integer id, HttpServletRequest request) {
        request.getHeader("username");
        return ResponseEntity.status(HttpStatus.CREATED, );
    }
    @Operation(summary = "Delete all games belonging to a given player")
    @DeleteMapping("/{id}/games")
    public ResponseEntity<GameDTO> deleteGames(@PathVariable("id") Integer id) {
        gameService.deleteByPlayer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Show all games for a given player")
    @GetMapping("/{id}/games")
    public ResponseEntity<List<GameDTO>> getGames(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(gameService.getByPlayerId(id), HttpStatus.OK);
    }
}
