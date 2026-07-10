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

import java.time.LocalDate;
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

        // controllo creazione e viaggio futuro su stato
        if (body.statoViaggio() == StatoViaggio.COMPLETATO && body.data().isAfter(LocalDate.now())) {
            throw new BadRequestException("Non puoi creare un viaggio COMPLETATO se la data di partenza (" + body.data() + ") è nel futuro");
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

        // controllo sullo stato nel futuro
        if (body.statoViaggio() == StatoViaggio.COMPLETATO && body.data().isAfter(LocalDate.now())) {
            throw new BadRequestException("Non puoi impostare un viaggio COMPLETATO se la data di partenza (" + body.data() + ") è nel futuro");
        }

        found.setDestinazione(body.destinazione());
        found.setData(body.data());
        found.setStatoViaggio(body.statoViaggio());

        return viaggioRepository.save(found);
    }

    // UPDATE STATO
    public Viaggio updateStato(UUID id, StatoViaggio nuovoStato) {
        Viaggio found = this.findById(id);

        // controllo se lo stato è completato non può essere modificato
        if (found.getStatoViaggio() == StatoViaggio.COMPLETATO) {
            throw new BadRequestException("Non puoi cambiare lo stato di un viaggio che è già: " + found.getStatoViaggio());
        }

        // controlla la data sulla variazione stato
        if (nuovoStato == StatoViaggio.COMPLETATO && found.getData().isAfter(LocalDate.now())) {
            throw new BadRequestException("Non puoi impostare questo viaggio come COMPLETATO perché la sua data (" + found.getData() + ") è nel futuro");
        }

        // controllo se lo stato immesso è identico a quello che è impostato
        if (found.getStatoViaggio() == nuovoStato) {
            throw new BadRequestException("Il viaggio con ID: " + id + " è già nello stato di: " + nuovoStato);
        }

        found.setStatoViaggio(nuovoStato);
        return viaggioRepository.save(found);
    }

}
