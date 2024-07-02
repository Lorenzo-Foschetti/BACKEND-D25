package lorenzofoschetti.u5d12.service;

import lorenzofoschetti.u5d12.entities.Dipendente;
import lorenzofoschetti.u5d12.exceptions.UnauthorizedException;
import lorenzofoschetti.u5d12.payloads.UserLoginPayload;
import lorenzofoschetti.u5d12.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private DipendenteService dipendenteService;

    @Autowired
    private JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(UserLoginPayload payload) {


        Dipendente user = this.dipendenteService.findByEmail(payload.email());

        if (user.getPassword().equals(payload.password())) {

            return jwtTools.createToken(user);
        } else {

            throw new UnauthorizedException("Credenziali non corrette!");
        }
    }
}
