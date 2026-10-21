package cl.brarriodigital.catalog.dto.response;

import java.util.List;
import java.util.UUID;

public record ProcedureTypeResponse(
    UUID id,
    String name,
    String description,
    int dailyQuota,
    int availableQuota,
    List<String> requirements,
    boolean active
) {
}
