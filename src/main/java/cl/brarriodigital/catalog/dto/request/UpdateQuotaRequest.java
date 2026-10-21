package cl.brarriodigital.catalog.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateQuotaRequest(
    @NotNull @PositiveOrZero Integer availableQuota
) {
}
