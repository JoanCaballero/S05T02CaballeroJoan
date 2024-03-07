package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Repository;

import cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.S05T02CaballeroJoan.Model.Domain.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    boolean existsByPlayerName(String name);
}
