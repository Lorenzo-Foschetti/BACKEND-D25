package lorenzofoschetti.u5d12.controllers;

import lorenzofoschetti.u5d12.entities.Dipendente;
import lorenzofoschetti.u5d12.exceptions.BadRequestException;
import lorenzofoschetti.u5d12.payloads.NewDipendentePayload;
import lorenzofoschetti.u5d12.payloads.UserLoginPayload;
import lorenzofoschetti.u5d12.payloads.UserLoginResponsePayload;
import lorenzofoschetti.u5d12.services.AuthService;
import lorenzofoschetti.u5d12.services.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private DipendenteService dipendenteService;

    @PostMapping("/login")
    public UserLoginResponsePayload login(@RequestBody UserLoginPayload payload) {
        return new UserLoginResponsePayload(authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)

    private Dipendente saveDipendente(@RequestBody @Validated NewDipendentePayload body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {

            throw new BadRequestException(validationResult.getAllErrors());
        }
        return dipendenteService.save(body);
    }

}