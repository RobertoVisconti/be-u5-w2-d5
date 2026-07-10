package robertovisconti.be_u5_w2_d5.controllers;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import robertovisconti.be_u5_w2_d5.DTO.ViaggioDTO;
import robertovisconti.be_u5_w2_d5.entities.Viaggio;
import robertovisconti.be_u5_w2_d5.enums.StatoViaggio;
import robertovisconti.be_u5_w2_d5.exceptions.ValidationExceptions;
import robertovisconti.be_u5_w2_d5.services.ViaggioService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/viaggi")
public class ViaggioController {

    public final ViaggioService viaggioService;

    public ViaggioController(ViaggioService viaggioService) {
        this.viaggioService = viaggioService;
    }

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio salvaViaggio(@RequestBody @Validated ViaggioDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> listaErrori = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationExceptions(listaErrori);
        }
        return viaggioService.save(body);
    }

    // GET ALL
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Viaggio> getAllViaggi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return viaggioService.findAll(page, size, sortBy);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Viaggio getViaggioById(@PathVariable UUID id) {
        return viaggioService.findById(id);
    }

    // PUT
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Viaggio modificaViaggio(@PathVariable UUID id, @RequestBody @Validated ViaggioDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            List<String> listaErrori = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationExceptions(listaErrori);
        }
        return viaggioService.updateViaggio(id, body);
    }

    // PATCH PER LO STATO
    @PatchMapping("/{id}/stato")
    @ResponseStatus(HttpStatus.OK)
    public Viaggio cambiaStatoViaggio(@PathVariable UUID id, @RequestParam StatoViaggio stato) {
        return viaggioService.updateStato(id, stato);
    }
}
