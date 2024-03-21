package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class PlayerDTO {

    private int id;
    private String playerName;
    private LocalDateTime registrationDate;
    private double winRate;
}
