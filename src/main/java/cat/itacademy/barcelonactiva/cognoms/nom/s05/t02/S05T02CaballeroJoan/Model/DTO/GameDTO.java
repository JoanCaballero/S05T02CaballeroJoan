package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class GameDTO {
    private int gameID;
    private int playerID;
    private int diceOne;
    private int diceTwo;
    private boolean won;
}
