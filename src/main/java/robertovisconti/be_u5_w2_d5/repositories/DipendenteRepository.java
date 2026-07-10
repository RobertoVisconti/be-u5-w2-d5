package robertovisconti.be_u5_w2_d5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import robertovisconti.be_u5_w2_d5.entities.Dipendente;

import java.util.UUID;

public interface DipendenteRepository extends JpaRepository<Dipendente, UUID> {

    boolean existsByEmail(String email);


    boolean existsByUsername(String username);
}
