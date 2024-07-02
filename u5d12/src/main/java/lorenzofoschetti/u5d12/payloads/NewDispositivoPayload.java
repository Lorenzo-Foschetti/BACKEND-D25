package lorenzofoschetti.u5d12.payloads;

import lorenzofoschetti.u5d12.enums.State;
import lorenzofoschetti.u5d12.enums.Type;

import java.util.UUID;

public record NewDispositivoPayload(
        Type type,
        State state,
        UUID dipendenteId
) {
}
