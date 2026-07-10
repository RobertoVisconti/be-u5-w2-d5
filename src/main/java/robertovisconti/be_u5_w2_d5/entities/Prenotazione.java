package robertovisconti.be_u5_w2_d5.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "prenotazione")
@Getter
@NoArgsConstructor
public class Prenotazione {

    @Id
    @GeneratedValue
    @Column(name = "id_prenotazione")
    private UUID id;

    @Column(name = "data_richiesta", nullable = false)
    private LocalDate dataRichiesta = LocalDate.now();

    @Column(nullable = false)
    private String note;

    @ManyToOne
    @JoinColumn(name = "id_viaggio", nullable = false)
    private Viaggio viaggio;

    @ManyToOne
    @JoinColumn(name = "id_dipendente", nullable = false)
    private Dipendente dipendente;

    public Prenotazione(LocalDate dataRichiesta, String note) {
        this.dataRichiesta = dataRichiesta;
        this.note = note;
    }
}
