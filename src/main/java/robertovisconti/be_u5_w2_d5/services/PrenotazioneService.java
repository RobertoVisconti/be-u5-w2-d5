package robertovisconti.be_u5_w2_d5.services;

import org.springframework.stereotype.Service;
import robertovisconti.be_u5_w2_d5.DTO.PrenotazioneDTO;
import robertovisconti.be_u5_w2_d5.entities.Dipendente;
import robertovisconti.be_u5_w2_d5.entities.Prenotazione;
import robertovisconti.be_u5_w2_d5.entities.Viaggio;
import robertovisconti.be_u5_w2_d5.exceptions.BadRequestException;
import robertovisconti.be_u5_w2_d5.exceptions.NotFoundException;
import robertovisconti.be_u5_w2_d5.repositories.DipendenteRepository;
import robertovisconti.be_u5_w2_d5.repositories.PrenotazioneRepository;
import robertovisconti.be_u5_w2_d5.repositories.ViaggioRepository;

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
        Dipendente dipendente = dipendenteRepository.findById(body.idDipendente()).orElseThrow(() -> new NotFoundException("Viaggio non trovato con ID: " + body.idDipendente()));

        // verifico che il viaggio esiste
        Viaggio viaggio = viaggioRepository.findById(body.idDipendente()).orElseThrow(() -> new NotFoundException("Viaggio non trovato con ID: " + body.idViaggio()));

        // controllo sulla prenotazione
        boolean giaPrenotato = prenotazioneRepository.existsByDipendenteAndViaggioData(dipendente, viaggio.getData());
        if (giaPrenotato) {
            throw new BadRequestException("Il dipendete ha già una prenotazione per il giorno " + viaggio.getData());
        }

        Prenotazione nuovaPrenotazione = new Prenotazione();
        nuovaPrenotazione.setDipendente(dipendente);
        nuovaPrenotazione.setViaggio(viaggio);
        nuovaPrenotazione.setNote(body.note());

        return prenotazioneRepository.save(nuovaPrenotazione);

    }
}
