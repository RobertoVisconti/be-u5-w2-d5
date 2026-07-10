package robertovisconti.be_u5_w2_d5.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_u5_w2_d5.DTO.PrenotazioneDTO;
import robertovisconti.be_u5_w2_d5.entities.Prenotazione;
import robertovisconti.be_u5_w2_d5.exceptions.ValidationExceptions;
import robertovisconti.be_u5_w2_d5.services.PrenotazioneService;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione salvaPrenotazione(@RequestBody @Validated PrenotazioneDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> listaErrori = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationExceptions(listaErrori);
        }
        return prenotazioneService.save(body);

    }
}
