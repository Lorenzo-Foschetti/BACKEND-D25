package lorenzofoschetti.u5d12.security;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lorenzofoschetti.u5d12.entities.Dipendente;
import lorenzofoschetti.u5d12.exceptions.UnauthorizedException;
import lorenzofoschetti.u5d12.service.DipendenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;


@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private DipendenteService dipendenteService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new

                    UnauthorizedException("Per favore inserisci correttamente il token nell'header");

        String accessToken = authHeader.substring(7);

        jwtTools.verifyToken(accessToken);

        String userId = jwtTools.extractIdFromToken(accessToken);
        Dipendente currentUser = dipe.findById(UUID.fromString(userId));

        // 4.2 Trovato l'utente posso associarlo al Security Context, praticamente questo equivale ad 'associare' l'utente autenticato alla richiesta corrente
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, currentUser.getAuthorities());
        // Il terzo parametro è OBBLIGATORIO se si vuol poter usare i vari @PreAuthorize perché esso contiene la lista dei ruoli dell'utente
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

        //se è tutto ok mandiamo un 401, ma dobbiamo ancora gestire questa cosa
    }


    //con l'override di questo metodo evitiamo di fare il controllo del token per i metodi di login e registrazione
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}