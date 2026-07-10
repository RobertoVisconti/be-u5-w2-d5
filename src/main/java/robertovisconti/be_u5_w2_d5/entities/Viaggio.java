package robertovisconti.be_u5_w2_d5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import robertovisconti.be_u5_w2_d5.enums.StatoViaggio;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "viaggio")
@Getter
@NoArgsConstructor
public class Viaggio {

    @Id
    @GeneratedValue
    @Column(name = "id_viaggio")
    private UUID id;

    @Column(nullable = false)
    private String destinazione;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "stato_viaggio", nullable = false)
    private StatoViaggio statoViaggio;

    public Viaggio(String destinazione, LocalDate data, StatoViaggio statoViaggio) {
        this.destinazione = destinazione;
        this.data = data;
        this.statoViaggio = statoViaggio;
    }

    public void setStatoViaggio(StatoViaggio statoViaggio) {
        this.statoViaggio = statoViaggio;
    }
}
