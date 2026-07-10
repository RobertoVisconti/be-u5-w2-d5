package robertovisconti.be_u5_w2_d5.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import robertovisconti.be_u5_w2_d5.entities.Viaggio;

import java.util.UUID;

@Repository
public interface ViaggioRepository extends JpaRepository<Viaggio, UUID> {
}
