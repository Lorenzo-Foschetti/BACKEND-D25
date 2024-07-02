package lorenzofoschetti.u5d12.service;

import lorenzofoschetti.u5d12.entities.Dipendente;
import lorenzofoschetti.u5d12.entities.Dispositivo;
import lorenzofoschetti.u5d12.exceptions.NotFoundException;
import lorenzofoschetti.u5d12.payloads.NewDispositivoPayload;
import lorenzofoschetti.u5d12.repositories.DispositivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DispositivoService {

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private DipendenteService dipendenteService;


    public Dispositivo save(NewDispositivoPayload body) {

        Dispositivo nuovoDispositivo = new Dispositivo(body.type(), body.state(), null);
        return dispositivoRepository.save(nuovoDispositivo);
    }

    public Dispositivo findDispositivoById(UUID id) {
        return dispositivoRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    public Page<Dispositivo> getDispositiviList(int page, int size, String sortBy) {
        if (size > 20) size = 20;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return dispositivoRepository.findAll(pageable);
    }

    public Dispositivo findByIdAndUpdate(UUID id, NewDispositivoPayload body) {
        Dispositivo found = this.findDispositivoById(id);
        found.setType(body.type());
        found.setState(body.state());
        return dispositivoRepository.save(found);

    }


    public void findByIdAndDelete(UUID id) {
        dispositivoRepository.delete(this.findDispositivoById(id));
    }


    public Dispositivo assegnazioneDispositivo(UUID dispositivoId, UUID dipendenteId) {
        Dispositivo foundDispositivo = this.findDispositivoById(dispositivoId);
        Dipendente foundDipendente = dipendenteService.findById(dipendenteId);
        foundDispositivo.setDipendente(foundDipendente);
        return dispositivoRepository.save(foundDispositivo);
    }
}