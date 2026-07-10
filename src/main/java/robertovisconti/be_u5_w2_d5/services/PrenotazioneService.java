package robertovisconti.be_u5_w2_d5.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import robertovisconti.be_u5_w2_d5.DTO.PrenotazioneDTO;
import robertovisconti.be_u5_w2_d5.entities.Dipendente;
import robertovisconti.be_u5_w2_d5.entities.Prenotazione;
import robertovisconti.be_u5_w2_d5.entities.Viaggio;
import robertovisconti.be_u5_w2_d5.enums.StatoViaggio;
import robertovisconti.be_u5_w2_d5.exceptions.BadRequestException;
import robertovisconti.be_u5_w2_d5.exceptions.NotFoundException;
import robertovisconti.be_u5_w2_d5.repositories.DipendenteRepository;
import robertovisconti.be_u5_w2_d5.repositories.PrenotazioneRepository;
import robertovisconti.be_u5_w2_d5.repositories.ViaggioRepository;

import java.util.UUID;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;
    private final ViaggioRepository viaggioRepository;
    private final DipendenteRepository dipendenteRepository;

    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, ViaggioRepository viaggioRepository, DipendenteRepository dipendenteRepository) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.viaggioRepository = viaggioRepository;
        this.dipendenteRepository = dipendenteRepository;
    }

    // SAVE
    public Prenotazione save(PrenotazioneDTO body) {

        // verifico se il dipendente esiste
        Dipendente dipendente = dipendenteRepository.findById(body.idDipendente()).orElseThrow(() -> new NotFoundException("Dipendente non trovato con ID: " + body.idDipendente()));

        // verifico che il viaggio esiste
        Viaggio viaggio = viaggioRepository.findById(body.idViaggio()).orElseThrow(() -> new NotFoundException("Viaggio non trovato con ID: " + body.idViaggio()));

        // controllo su stato viaggio
        if (viaggio.getStatoViaggio() == StatoViaggio.COMPLETATO) {
            throw new BadRequestException("Non puoi prenotare un viaggio già: " + viaggio.getStatoViaggio());
        }

        // controllo sulla prenotazione
        boolean giaPrenotato = prenotazioneRepository.existsByDipendenteAndViaggioData(dipendente, viaggio.getData());
        if (giaPrenotato) {
            throw new BadRequestException("Il dipendete con username: " + dipendente.getUsername() + " ha già una prenotazione per il giorno " + viaggio.getData());
        }

        Prenotazione nuovaPrenotazione = new Prenotazione();
        nuovaPrenotazione.setDipendente(dipendente);
        nuovaPrenotazione.setViaggio(viaggio);
        nuovaPrenotazione.setNote(body.note());

        return prenotazioneRepository.save(nuovaPrenotazione);

    }

    // FIND BY ID
    public Prenotazione findById(UUID id) {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException("Prenotazione non trovata con ID: " + id));
    }


    // FIND BY ID AND RETURN LIST
    public Page<Prenotazione> findAll(int page, int size, String sortBy) {
        if (size > 15) size = 15;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return prenotazioneRepository.findAll(pageable);
    }

    // UPDATE
    public Prenotazione updatePrenotazione(UUID id, PrenotazioneDTO body) {

        Prenotazione found = this.findById(id);

        Dipendente dipendente = dipendenteRepository.findById(body.idDipendente())
                .orElseThrow(() -> new NotFoundException("Dipendente non trovato con ID: " + body.idDipendente()));

        Viaggio viaggio = viaggioRepository.findById(body.idViaggio())
                .orElseThrow(() -> new NotFoundException("Viaggio non trovato con ID: " + body.idViaggio()));

        if (viaggio.getStatoViaggio() == StatoViaggio.COMPLETATO) {
            throw new BadRequestException("Non puoi assegnare una prenotazione a un viaggio già con stato: " + viaggio.getStatoViaggio());
        }

        // controllo per far si che se volessi cambiare la nota di un viaggio che è settato nel giorno stesso riesco, ppichè la persona è già occupa quel giorno
        boolean giaPrenotato = prenotazioneRepository.existsByDipendenteAndViaggioDataAndIdNot(
                dipendente,
                viaggio.getData(),
                id
        );

        if (giaPrenotato) {
            throw new BadRequestException("Il dipendente con username: " + dipendente.getUsername() +
                    " ha già una prenotazione per il giorno " + viaggio.getData());
        }

        found.setDipendente(dipendente);
        found.setViaggio(viaggio);
        found.setNote(body.note());

        return prenotazioneRepository.save(found);
    }
}
