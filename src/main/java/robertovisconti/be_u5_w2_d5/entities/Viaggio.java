package robertovisconti.be_u5_w2_d5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import robertovisconti.be_u5_w2_d5.enums.StatoViaggio;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "viaggio")
@Getter
@NoArgsConstructor
@ToString
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
    @Enumerated(EnumType.STRING)
    private StatoViaggio statoViaggio;

    public Viaggio(String destinazione, LocalDate data, StatoViaggio statoViaggio) {
        this.destinazione = destinazione;
        this.data = data;
        this.statoViaggio = statoViaggio;
    }

    public void setDestinazione(String destinazione) {
        this.destinazione = destinazione;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setStatoViaggio(StatoViaggio statoViaggio) {
        this.statoViaggio = statoViaggio;
    }
}
