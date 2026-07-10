package robertovisconti.be_u5_w2_d5.controllers;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_u5_w2_d5.DTO.PrenotazioneDTO;
import robertovisconti.be_u5_w2_d5.entities.Prenotazione;
import robertovisconti.be_u5_w2_d5.exceptions.ValidationExceptions;
import robertovisconti.be_u5_w2_d5.services.PrenotazioneService;

import java.util.List;
import java.util.UUID;

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

    // GET LIST
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Prenotazione> getAllPrenotazioni(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return prenotazioneService.findAll(page, size, sortBy);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Prenotazione getPrenotazioneById(@PathVariable UUID id) {
        return prenotazioneService.findById(id);
    }

    // PUT
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Prenotazione modificaPrenotazione(
            @PathVariable UUID id,
            @RequestBody @Validated PrenotazioneDTO body, BindingResult validation
    ) {
        if (validation.hasErrors()) {
            List<String> listaErrori = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationExceptions(listaErrori);
        }
        return prenotazioneService.updatePrenotazione(id, body);
    }


}
