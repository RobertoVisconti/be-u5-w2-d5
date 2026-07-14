package robertovisconti.be_u5_w2_d5.services;

import org.springframework.stereotype.Service;
import robertovisconti.be_u5_w2_d5.DTO.LoginDTO;
import robertovisconti.be_u5_w2_d5.entities.Dipendente;
import robertovisconti.be_u5_w2_d5.exceptions.UnauthorizedException;
import robertovisconti.be_u5_w2_d5.security.JWTTools;

@Service
public class AuthService {

    private final JWTTools jwtTools;
    private final DipendenteService dipendenteService;

    public AuthService(DipendenteService dipendenteService, JWTTools jwtTools) {
        this.dipendenteService = dipendenteService;
        this.jwtTools = jwtTools;
    }

    // Metodo controllo credenziali
    public String checkCredentialsAndGenerateToken(LoginDTO body) {

        // 1. controllo email
        Dipendente found = this.dipendenteService.findByEmail(body.email());

        // 1.2 controllo delle password se corrispondono
        if (found.getPassword().equals(body.password())) {
            // 2.0 OK -> Genero un access token
            return this.jwtTools.generateToken(found);
        } else {
            // 3.0 NO -> 401 errore credenziali
            throw new UnauthorizedException("Credenziali errate");
        }
        
    }
}
