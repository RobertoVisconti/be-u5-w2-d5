package robertovisconti.be_u5_w2_d5.DTO;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record PrenotazioneDTO(

        @NotNull(message = "L'ID del dipendente è obbligatorio")
        UUID idDipendente,

        @NotNull(message = "L'ID del viaggio è obbligatorio")
        UUID idViaggio,

        String note
) {
}
