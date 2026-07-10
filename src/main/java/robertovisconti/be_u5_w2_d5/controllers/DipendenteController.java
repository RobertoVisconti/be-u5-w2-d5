package robertovisconti.be_u5_w2_d5.controllers;


import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_u5_w2_d5.DTO.DipendenteDTO;
import robertovisconti.be_u5_w2_d5.entities.Dipendente;
import robertovisconti.be_u5_w2_d5.exceptions.ValidationExceptions;
import robertovisconti.be_u5_w2_d5.services.DipendenteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/dipendenti")
public class DipendenteController {

    private final DipendenteService dipendenteService;

    public DipendenteController(DipendenteService dipendenteService) {
        this.dipendenteService = dipendenteService;
    }

    // POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente salvaDipendente(
            @ModelAttribute @Validated DipendenteDTO body, BindingResult validation,
            @RequestParam(value = "imgProfile", required = false) MultipartFile file
    ) {
        if (validation.hasErrors()) {
            List<String> listaErrori = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationExceptions(listaErrori);
        }
        return dipendenteService.save(body, file);
    }

    // GET ALL
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<Dipendente> getAllDipendenti(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username") String sortBy
    ) {
        return dipendenteService.findAll(page, size, sortBy);
    }

    // GET BY ID
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Dipendente getDipendenteById(@PathVariable UUID id) {
        return dipendenteService.findById(id);
    }

    // PUT
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Dipendente modificaDipendente(
            @PathVariable UUID id,
            @ModelAttribute @Validated DipendenteDTO body, BindingResult validation,
            @RequestParam(value = "imgProfile", required = false) MultipartFile file
    ) {
        if (validation.hasErrors()) {
            List<String> listaErrori = validation.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationExceptions(listaErrori);
        }
        return dipendenteService.updateDipendente(id, body, file);
    }
}
