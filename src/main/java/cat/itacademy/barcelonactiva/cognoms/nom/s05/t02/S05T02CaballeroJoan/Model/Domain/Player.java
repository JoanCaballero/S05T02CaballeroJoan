package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "PlayerDTO")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long playerID;

    @Column(name = "Player_Name")
    private String playerName;

    @Column(name = "Registration_Date")
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "player")
    private List<Game> gamesList;

    public void addGame(Game game){
        if(gamesList == null){
            gamesList = new ArrayList<>();
        }
        gamesList.add(game);
    }

    public double winRate(){
        if(gamesList != null && !gamesList.isEmpty()){
            return (double) gamesList.size()/gamesList.stream().filter(Game::isWon).count() * 100;
        }else{
            return 0.0;
        }
    }
}
