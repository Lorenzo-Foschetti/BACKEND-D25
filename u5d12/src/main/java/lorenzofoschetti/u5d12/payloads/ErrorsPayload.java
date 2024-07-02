package lorenzofoschetti.u5d12.payloads;

import java.time.LocalDateTime;

public record ErrorsPayload(String message, LocalDateTime errorTime) {
}
