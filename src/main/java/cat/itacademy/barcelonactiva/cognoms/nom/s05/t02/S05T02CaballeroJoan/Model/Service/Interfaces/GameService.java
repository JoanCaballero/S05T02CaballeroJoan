package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Service.Interfaces;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.DTO.GameDTO;

import java.util.List;

public interface GameService {
    GameDTO playerPlayGame(int id);
    void deleteGames(int id);
    List<GameDTO> listGamesByPlayer(int id);
}
