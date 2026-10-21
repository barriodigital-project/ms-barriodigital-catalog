package cl.brarriodigital.catalog.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record CreateProcedureTypeRequest(
    @NotBlank String name,
    @NotBlank String description,
    @NotNull @PositiveOrZero Integer dailyQuota,
    List<@NotBlank String> requirements
) {
}
