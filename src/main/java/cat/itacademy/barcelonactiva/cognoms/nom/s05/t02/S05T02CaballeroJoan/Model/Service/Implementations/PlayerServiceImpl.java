package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Service.Implementations;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.DTO.PlayerDTO;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Domain.Game;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Domain.Player;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Exceptions.InvalidUsernameException;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Repository.GameRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Repository.PlayerRepository;
import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Service.Interfaces.PlayerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;

    @Override
    public PlayerDTO save(PlayerDTO playerDTO) {
        String playerName = playerDTO.getPlayerName();
        if (playerName == null || playerName.trim().isEmpty()) {
            playerDTO.setPlayerName("ANONIM");
        } else {
            if (playerRepository.existsByPlayerName(playerName)) {
                throw new InvalidUsernameException("This username already exists.");
            }
        }

        if (playerDTO.getRegistrationDate() == null) {
            playerDTO.setRegistrationDate(LocalDateTime.now());
        }

        if (playerDTO.getWinRate() == 0.0) {
            playerDTO.setWinRate(0.0);
        }
        Player savedPlayer = playerRepository.save(toEntity(playerDTO));
        return toDTO(savedPlayer);
    }

    @Override
    public PlayerDTO update(PlayerDTO playerDTO) {
        findById(playerDTO.getId());
        return toDTO(playerRepository.save(toEntity(playerDTO)));
    }

    @Override
    public PlayerDTO findById(int id) {
        return toDTO(playerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Player not found.")));
    }

    @Override
    public List<PlayerDTO> findAll() {
        return playerRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public double avgWinRate() {
        List<PlayerDTO> players = findAll();

        if(players.isEmpty()){
            return  0;
        }else{
            return players.stream()
                    .mapToDouble(PlayerDTO::getWinRate)
                    .average()
                    .orElse(0.0);
        }
    }

    @Override
    public PlayerDTO loser() {
        List<PlayerDTO> players = findAll();
        return players.stream().min(Comparator.comparing(PlayerDTO::getWinRate)).orElseThrow(()-> new ArrayIndexOutOfBoundsException("No players found."));
    }

    @Override
    public PlayerDTO winner() {
        List<PlayerDTO> players = findAll();
        return players.stream().max(Comparator.comparing(PlayerDTO::getWinRate)).orElseThrow(()-> new ArrayIndexOutOfBoundsException("No players found."));
    }

    private double getPlayerWinRate(Player player){
        List<Game> games = gameRepository.findByPlayerId(player.getId());
        if(games.isEmpty()){
            return 0;
        }else{
            double avg = games.stream().filter(Game::won).count();
            return avg/games.size();
        }
    }

    public PlayerDTO toDTO(Player player){
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setId(player.getId());
        playerDTO.setPlayerName(player.getPlayerName());
        playerDTO.setRegistrationDate(player.getRegistrationDate());
        playerDTO.setWinRate(getPlayerWinRate(player));
        return playerDTO;
    }

    public Player toEntity(PlayerDTO playerDTO){
        Player player = new Player();
        player.setId(playerDTO.getId());
        player.setPlayerName(playerDTO.getPlayerName());
        player.setRegistrationDate(playerDTO.getRegistrationDate());
        return player;
    }
}
