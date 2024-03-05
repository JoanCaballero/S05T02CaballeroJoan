package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="GameDTO")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long gameID;

    @Column(name = "Dice_One")
    private int diceOne;

    @Column(name = "Dice_Two")
    private int diceTwo;

    @Column(name="result")
    private boolean won;

    @ManyToOne
    @JoinColumn(name = "playerID")
    private Player player;

    public boolean won(){
        return diceOne + diceTwo == 7;
    }
}
