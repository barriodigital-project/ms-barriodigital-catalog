package cl.brarriodigital.catalog.service;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cl.brarriodigital.catalog.dto.request.CreateProcedureTypeRequest;
import cl.brarriodigital.catalog.dto.request.UpdateProcedureTypeRequest;
import cl.brarriodigital.catalog.dto.request.UpdateQuotaRequest;
import cl.brarriodigital.catalog.dto.response.AvailabilityResponse;
import cl.brarriodigital.catalog.dto.response.ProcedureTypeResponse;
import cl.brarriodigital.catalog.exception.QuotaConflictException;
import cl.brarriodigital.catalog.exception.ResourceNotFoundException;
import cl.brarriodigital.catalog.model.ProcedureType;
import cl.brarriodigital.catalog.repository.ProcedureTypeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcedureTypeService {

    private final ProcedureTypeRepository repository;

    @Transactional(readOnly = true)
    public List<ProcedureTypeResponse> findAll() {
        return repository.findAllByOrderByNameAscIdAsc().stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public ProcedureTypeResponse create(CreateProcedureTypeRequest request) {
        var procedureType = new ProcedureType();
        procedureType.setName(request.name().trim());
        procedureType.setDescription(request.description().trim());
        procedureType.setDailyQuota(request.dailyQuota());
        procedureType.setAvailableQuota(request.dailyQuota());
        procedureType.setRequirements(normalizeRequirements(request.requirements()));
        procedureType.setActive(true);
        return toResponse(repository.save(procedureType));
    }

    @Transactional(readOnly = true)
    public ProcedureTypeResponse findById(UUID id) {
        return toResponse(getProcedureType(id));
    }

    @Transactional
    public ProcedureTypeResponse update(UUID id, UpdateProcedureTypeRequest request) {
        var procedureType = getProcedureType(id);
        procedureType.setName(request.name().trim());
        procedureType.setDescription(request.description().trim());
        procedureType.setDailyQuota(request.dailyQuota());
        procedureType.setAvailableQuota(request.dailyQuota());
        procedureType.setRequirements(normalizeRequirements(request.requirements()));
        return toResponse(repository.save(procedureType));
    }

    @Transactional(readOnly = true)
    public AvailabilityResponse getAvailability(UUID id) {
        var procedureType = getProcedureType(id);
        return new AvailabilityResponse(
            procedureType.getId(),
            procedureType.getDailyQuota(),
            procedureType.getAvailableQuota(),
            procedureType.isActive() && procedureType.getAvailableQuota() > 0
        );
    }

    @Transactional
    public ProcedureTypeResponse updateQuota(UUID id, UpdateQuotaRequest request) {
        var procedureType = repository.findByIdForUpdate(id)
            .orElseThrow(() -> notFound(id));
        if (request.availableQuota() > procedureType.getDailyQuota()) {
            throw new QuotaConflictException("El cupo disponible no puede superar el cupo diario");
        }
        procedureType.setAvailableQuota(request.availableQuota());
        return toResponse(repository.save(procedureType));
    }

    private ProcedureType getProcedureType(UUID id) {
        return repository.findById(id).orElseThrow(() -> notFound(id));
    }

    private ResourceNotFoundException notFound(UUID id) {
        return new ResourceNotFoundException("Tipo de trámite no encontrado: " + id);
    }

    private List<String> normalizeRequirements(List<String> requirements) {
        return requirements == null
            ? new ArrayList<>()
            : new ArrayList<>(requirements.stream().map(String::trim).toList());
    }

    private ProcedureTypeResponse toResponse(ProcedureType procedureType) {
        return new ProcedureTypeResponse(
            procedureType.getId(),
            procedureType.getName(),
            procedureType.getDescription(),
            procedureType.getDailyQuota(),
            procedureType.getAvailableQuota(),
            List.copyOf(procedureType.getRequirements()),
            procedureType.isActive()
        );
    }
}
