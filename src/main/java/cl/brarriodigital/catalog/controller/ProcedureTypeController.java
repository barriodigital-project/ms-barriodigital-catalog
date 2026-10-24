package cl.brarriodigital.catalog.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cl.brarriodigital.catalog.dto.request.CreateProcedureTypeRequest;
import cl.brarriodigital.catalog.dto.request.UpdateProcedureTypeRequest;
import cl.brarriodigital.catalog.dto.request.UpdateQuotaRequest;
import cl.brarriodigital.catalog.dto.response.AvailabilityResponse;
import cl.brarriodigital.catalog.dto.response.ProcedureTypeResponse;
import cl.brarriodigital.catalog.service.ProcedureTypeService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/procedure-types")
@RequiredArgsConstructor
public class ProcedureTypeController {

    private final ProcedureTypeService service;

    @GetMapping
    public List<ProcedureTypeResponse> findAll() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProcedureTypeResponse create(@Valid @RequestBody CreateProcedureTypeRequest request) {
        return service.create(request);
    }

    @GetMapping("/{id}")
    public ProcedureTypeResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("/{id}")
    public ProcedureTypeResponse update(
        @PathVariable UUID id,
        @Valid @RequestBody UpdateProcedureTypeRequest request
    ) {
        return service.update(id, request);
    }

    @GetMapping("/{id}/availability")
    public AvailabilityResponse getAvailability(@PathVariable UUID id) {
        return service.getAvailability(id);
    }

    @PatchMapping("/{id}/quota")
    public ProcedureTypeResponse updateQuota(
        @PathVariable UUID id,
        @Valid @RequestBody UpdateQuotaRequest request
    ) {
        return service.updateQuota(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
