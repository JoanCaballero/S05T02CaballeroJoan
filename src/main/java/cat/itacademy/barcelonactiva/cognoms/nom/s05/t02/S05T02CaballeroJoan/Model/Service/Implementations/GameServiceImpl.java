package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Service.Implementations;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.DTO.GameDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Repository.GameRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Repository.PlayerRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Service.Interfaces.GameService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class GameServiceImpl implements GameService {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public GameDTO playerPlayGame(long id) {
        Player player = playerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Player not found."));
        Game game = new Game();
        Player newPlayer = new Player();
        diceRoll(game);
        game.setPlayer(newPlayer);
        newPlayer.addGame(game);
        return toDTO(gameRepository.save(game));
    }

    @Transactional
    @Override
    public void deleteGames(long id) {
        playerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Player not found."));
        gameRepository.deleteByPlayerId(id);
    }

    @Override
    public List<GameDTO> listGamesByPlayer(long id) {
        playerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Player not found."));
        return gameRepository.findByPlayerId(id).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public void diceRoll(Game game){
        Random rdm = new Random();
        game.setDiceOne(rdm.nextInt(6)+1);
        game.setDiceOne(rdm.nextInt(6)+1);
        game.setWon(game.won());
    }

    public GameDTO toDTO(Game game){
        return new GameDTO(game.getGameID(), game.getDiceOne(), game.getDiceTwo(), game.isWon());
    }
}
