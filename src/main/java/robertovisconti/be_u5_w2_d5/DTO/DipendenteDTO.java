package robertovisconti.be_u5_w2_d5.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotBlank(message = "Lo username è obbligatorio")
        @Size(min = 3, max = 20, message = "Lo username deve essere tra 3 e 20 caratteri")
        String username,

        @NotBlank(message = "Il nome è obbligatorio")
        String nome,

        @NotBlank(message = "Il cognome è obbligatorio")
        String cognome,

        @NotBlank(message = "L'e-mail è obbligatoria")
        @Email(message = "Inserisci un indirizzo e-mail valido")
        String email
) {
}
