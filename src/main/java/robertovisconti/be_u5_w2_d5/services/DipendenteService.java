package robertovisconti.be_u5_w2_d5.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import robertovisconti.be_u5_w2_d5.DTO.DipendenteDTO;
import robertovisconti.be_u5_w2_d5.entities.Dipendente;
import robertovisconti.be_u5_w2_d5.exceptions.BadRequestException;
import robertovisconti.be_u5_w2_d5.exceptions.NotFoundException;
import robertovisconti.be_u5_w2_d5.repositories.DipendenteRepository;

import java.io.IOException;
import java.util.UUID;

@Service
public class DipendenteService {


    private final DipendenteRepository dipendenteRepository;
    private final Cloudinary fileUploader;

    public DipendenteService(DipendenteRepository dipendenteRepository, Cloudinary fileUploader) {
        this.dipendenteRepository = dipendenteRepository;
        this.fileUploader = fileUploader;
    }


    // SAVE
    public Dipendente save(DipendenteDTO body, MultipartFile file) {
        if (dipendenteRepository.existsByEmail(body.email())) {
            throw new BadRequestException("L'email " + body.email() + " è già in uso");
        }
        if (dipendenteRepository.existsByUsername(body.username())) {
            throw new BadRequestException("Lo username: " + body.username() + " è già in uso");
        }
        Dipendente nuovo = new Dipendente();
        nuovo.setNome(body.nome());
        nuovo.setCognome(body.cognome());
        nuovo.setEmail(body.email());
        nuovo.setPassword(body.password());

        nuovo.setUsername(body.username());

        // se carica l'avatar cambia quello di dafault
        if (file != null && !file.isEmpty()) {
            try {
                String url = (String) fileUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
                nuovo.setImgProfile(url);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }
        return dipendenteRepository.save(nuovo);
    }


    // FIND ALL
    public Page<Dipendente> findAll(int page, int size, String sortBy) {
        if (size > 15) size = 15;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dipendenteRepository.findAll(pageable);
    }

    // FIND BY ID
    public Dipendente findById(UUID id) {
        return dipendenteRepository.findById(id).orElseThrow(() -> new NotFoundException("Dipendente con ID: " + id + " non trovato"));
    }

    // PUT
    public Dipendente updateDipendente(UUID id, DipendenteDTO body, MultipartFile file) {

        Dipendente found = this.findById(id);

        if (!found.getEmail().equals(body.email()) && dipendenteRepository.existsByEmail(body.email())) {
            throw new BadRequestException("La nuova e-mail " + body.email() + " è già in uso");
        }
        if (!found.getUsername().equals(body.username()) && dipendenteRepository.existsByUsername(body.username())) {
            throw new BadRequestException("Il nuovo username " + body.username() + " è già in uso");
        }

        found.setNome(body.nome());
        found.setCognome(body.cognome());
        found.setEmail(body.email());
        found.setUsername(body.username());

        if (file != null && !file.isEmpty()) {
            try {
                String url = (String) fileUploader.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
                found.setImgProfile(url);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

        }

        return dipendenteRepository.save(found);

    }

    public Dipendente findByEmail(String email) {
        return this.dipendenteRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("L'utente con e-mail: " + email + " non è stato trovato"));
    }

}
