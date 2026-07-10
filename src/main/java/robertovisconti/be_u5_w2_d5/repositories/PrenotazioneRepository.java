package robertovisconti.be_u5_w2_d5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import robertovisconti.be_u5_w2_d5.entities.Prenotazione;

import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
}
