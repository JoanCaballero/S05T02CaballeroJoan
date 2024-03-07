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
        if(playerDTO.getPlayerName().isBlank() || playerDTO.getPlayerName().equalsIgnoreCase("ANONIM")){
            playerDTO.setPlayerName("ANONIM");
        } else if(playerRepository.existsByPlayerName(playerDTO.getPlayerName())){
            throw new InvalidUsernameException("This username already exists.");
        }
        playerDTO.setRegistrationDate(LocalDateTime.now());
        return toDTO(playerRepository.save(toEntity(playerDTO)));
    }

    @Override
    public PlayerDTO update(PlayerDTO playerDTO) {
        findById(playerDTO.getPlayerID());
        return toDTO(playerRepository.save(toEntity(playerDTO)));
    }

    @Override
    public PlayerDTO findById(long id) {
        return toDTO(playerRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Player not found.")));
    }

    @Override
    public List<PlayerDTO> findAll() {
        return playerRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public double avgWinRate() {
        List<Player> players = playerRepository.findAll();

        if(!players.isEmpty()){
            return players.stream().mapToDouble(Player::winRate).sum()/players.size();
        }else{
            throw new ArrayIndexOutOfBoundsException("No players found.");
        }
    }

    @Override
    public PlayerDTO loser() {
        List<Player> players = playerRepository.findAll();
        return toDTO(players.stream().min(Comparator.comparing(Player::winRate)).orElseThrow(()-> new ArrayIndexOutOfBoundsException("No players found.")));
    }

    @Override
    public PlayerDTO winner() {
        List<Player> players = playerRepository.findAll();
        return toDTO(players.stream().max(Comparator.comparing(Player::winRate)).orElseThrow(()-> new ArrayIndexOutOfBoundsException("No players found.")));
    }

    public PlayerDTO toDTO(Player player){
        return new PlayerDTO(player.getPlayerID(), player.getPlayerName(), player.getRegistrationDate());
    }

    public Player toEntity(PlayerDTO playerDTO){
        Player player = new Player();

        player.setPlayerID(player.getPlayerID());
        player.setPlayerName(playerDTO.getPlayerName());
        player.setRegistrationDate(playerDTO.getRegistrationDate());
        return player;
    }
}
