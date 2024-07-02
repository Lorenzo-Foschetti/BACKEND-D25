package lorenzofoschetti.u5d12.controllers;

import lorenzofoschetti.u5d12.entities.Dispositivo;
import lorenzofoschetti.u5d12.exceptions.BadRequestException;
import lorenzofoschetti.u5d12.payloads.NewDispositivoPayload;
import lorenzofoschetti.u5d12.service.DispositivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/dispositivi")
public class DispositivoController {

    @Autowired
    private DispositivoService dispositivoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    private Dispositivo saveDispositivo(@RequestBody @Validated NewDispositivoPayload body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {

            throw new BadRequestException(validationResult.getAllErrors());
        }
        return dispositivoService.save(body);
    }

    @GetMapping
    private Page<Dispositivo> getAllDispositivi(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "id") String sortBy) {
        return this.dispositivoService.getDispositiviList(page, size, sortBy);
    }


    @GetMapping("/{dispositivoId}")
    public Dispositivo findById(@PathVariable UUID dispositivoId) {
        return dispositivoService.findDispositivoById(dispositivoId);
    }

    @PutMapping("/{dispositivoId}")
    public Dispositivo findByIdAndUpdate(@PathVariable UUID dispositivoId, @RequestBody NewDispositivoPayload dispositivoModificato) {
        return dispositivoService.findByIdAndUpdate(dispositivoId, dispositivoModificato);
    }

    @DeleteMapping("/{dispositivoId}")
    public void findByIdAndDelete(@PathVariable UUID dipendenteId) {
        dispositivoService.findByIdAndDelete(dipendenteId);

    }

    @PatchMapping("/{dispositivoId}/{dipendenteId}")
    public Dispositivo assegnazioneDispositivo(@PathVariable UUID dispositivoId, @PathVariable UUID dipendenteId) {
        return dispositivoService.assegnazioneDispositivo(dispositivoId, dipendenteId);
    }
}

