package lorenzofoschetti.u5d12.payloads;

import jakarta.validation.constraints.NotEmpty;

public record NewDipendentePayload(
        @NotEmpty(message = "Il nome è obbligatorio!")
        String name,
        @NotEmpty(message = "Il cognome è obbligatorio!")
        String surname,
        @NotEmpty(message = "Lo username è obbligatorio!")
        String username,
        @NotEmpty(message = "L'email è obbligatoria!")
        String email,
        @NotEmpty(message = "Devi inserire la password")
        String password
) {
}
