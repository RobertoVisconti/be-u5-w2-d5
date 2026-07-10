package robertovisconti.be_u5_w2_d5.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import robertovisconti.be_u5_w2_d5.DTO.ViaggioDTO;
import robertovisconti.be_u5_w2_d5.entities.Viaggio;
import robertovisconti.be_u5_w2_d5.enums.StatoViaggio;
import robertovisconti.be_u5_w2_d5.exceptions.BadRequestException;
import robertovisconti.be_u5_w2_d5.exceptions.NotFoundException;
import robertovisconti.be_u5_w2_d5.repositories.ViaggioRepository;

import java.util.UUID;

@Service
public class ViaggioService {

    private final ViaggioRepository viaggioRepository;

    public ViaggioService(ViaggioRepository viaggioRepository) {
        this.viaggioRepository = viaggioRepository;
    }

    // SAVE
    public Viaggio save(ViaggioDTO body) {
        // controllo se il viaggio esiste già
        if (viaggioRepository.existsByDestinazioneAndData(body.destinazione(), body.data())) {
            throw new BadRequestException("Esiste già un viaggio pianificato per " + body.destinazione() + " in data: " + body.data());
        }

        Viaggio nuovoViaggio = new Viaggio();
        nuovoViaggio.setDestinazione(body.destinazione());
        nuovoViaggio.setData(body.data());
        nuovoViaggio.setStatoViaggio(body.statoViaggio());

        return viaggioRepository.save(nuovoViaggio);
    }


    // FIND ALL
    public Page<Viaggio> findAll(int page, int size, String sortBy) {
        if (size > 15) size = 15;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return viaggioRepository.findAll(pageable);
    }

    // FIND BY ID
    public Viaggio findById(UUID id) {
        return viaggioRepository.findById(id).orElseThrow(() -> new NotFoundException("Viaggio non trovato con ID: " + id));
    }

    // UPDATE VIAGGIO
    public Viaggio updateViaggio(UUID id, ViaggioDTO body) {
        Viaggio found = this.findById(id);

        // controllo se il viaggio è già completato
        if (found.getStatoViaggio() == StatoViaggio.COMPLETATO) {
            throw new BadRequestException("Non puoi modificare un viaggio che è già in stato di: " + body.statoViaggio());
        }
        found.setDestinazione(body.destinazione());
        found.setData(body.data());
        found.setStatoViaggio(body.statoViaggio());

        return viaggioRepository.save(found);
    }

    // UPDATE STATO
    public Viaggio updateStato(UUID id, StatoViaggio nuovoStato) {
        Viaggio found = this.findById(id);
        if (found.getStatoViaggio() == nuovoStato) {
            throw new BadRequestException("Il viaggio con ID: " + id + " è già nello stato di: " + nuovoStato);
        }

        found.setStatoViaggio(nuovoStato);
        return viaggioRepository.save(found);
    }

}
