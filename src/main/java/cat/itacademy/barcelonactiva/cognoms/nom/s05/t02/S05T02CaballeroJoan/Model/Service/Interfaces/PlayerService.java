package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Service.Interfaces;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.DTO.PlayerDTO;

import java.util.List;

public interface PlayerService {
    PlayerDTO save (PlayerDTO playerDTO);
    PlayerDTO update (PlayerDTO playerDTO);
    PlayerDTO findById (long id);
    List<PlayerDTO> findAll ();
    double avgWinRate();
    PlayerDTO loser();
    PlayerDTO winner();
}
