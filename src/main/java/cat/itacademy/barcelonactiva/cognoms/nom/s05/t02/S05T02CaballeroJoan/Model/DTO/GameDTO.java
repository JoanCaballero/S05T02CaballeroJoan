package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GameDTO {

    private long gameID;
    private int diceOne;
    private int diceTwo;
    private boolean won;
}
