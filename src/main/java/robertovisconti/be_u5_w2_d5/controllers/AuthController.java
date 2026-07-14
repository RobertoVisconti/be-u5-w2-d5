package robertovisconti.be_u5_w2_d5.controllers;


import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_u5_w2_d5.DTO.DipendenteDTO;
import robertovisconti.be_u5_w2_d5.DTO.LoginDTO;
import robertovisconti.be_u5_w2_d5.DTO.LoginResponseDTO;
import robertovisconti.be_u5_w2_d5.entities.Dipendente;
import robertovisconti.be_u5_w2_d5.services.AuthService;
import robertovisconti.be_u5_w2_d5.services.DipendenteService;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final DipendenteService dipendenteService;

    public AuthController(AuthService authService, DipendenteService dipendenteService) {
        this.authService = authService;
        this.dipendenteService = dipendenteService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO body) {
        return new LoginResponseDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public Dipendente saveUser(@RequestBody @Validated DipendenteDTO body, @RequestParam MultipartFile file, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList();
            throw new ValidationException();
        }
        Dipendente saved = this.dipendenteService.save(body, file);
        return new DipendenteDTO(saved.getId());
    }
}
