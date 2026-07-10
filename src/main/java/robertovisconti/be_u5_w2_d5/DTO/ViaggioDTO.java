package robertovisconti.be_u5_w2_d5.DTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import robertovisconti.be_u5_w2_d5.enums.StatoViaggio;

import java.time.LocalDate;

public record ViaggioDTO(
        @NotBlank(message = "La destinazione è obbligatoria")
        String destinazione,

        @NotNull(message = "La data del viaggio è obbligatoria")
        @FutureOrPresent(message = "La data del viaggio non può essere nel passato")
        LocalDate data,

        @NotNull(message = "Lo stato del viaggio è obbligatorio")
        StatoViaggio statoViaggio
) {
}
