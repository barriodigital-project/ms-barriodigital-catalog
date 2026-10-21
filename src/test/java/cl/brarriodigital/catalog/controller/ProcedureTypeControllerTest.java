package cl.brarriodigital.catalog.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import cl.brarriodigital.catalog.model.ProcedureType;
import cl.brarriodigital.catalog.repository.ProcedureTypeRepository;

@SpringBootTest
@AutoConfigureMockMvc
class ProcedureTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProcedureTypeRepository repository;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void createsListsAndReadsProcedureTypes() throws Exception {
        mockMvc.perform(post("/api/v1/procedure-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name":"Licencia","description":"Renovación","dailyQuota":8,
                     "requirements":["Cédula","Licencia anterior"]}
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.availableQuota").value(8))
            .andExpect(jsonPath("$.requirements.length()").value(2));

        mockMvc.perform(get("/api/v1/procedure-types"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void updatesProcedureTypeAndResetsAvailableQuotaToDailyQuota() throws Exception {
        var procedureType = saveProcedureType();

        mockMvc.perform(put("/api/v1/procedure-types/{id}", procedureType.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {"name":"Licencia renovada","description":"Nueva descripción",
                     "dailyQuota":12,"requirements":["Cédula"]}
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.dailyQuota").value(12))
            .andExpect(jsonPath("$.availableQuota").value(12));
    }

    @Test
    void reportsAvailabilityAndRejectsQuotaAboveDailyLimit() throws Exception {
        var procedureType = saveProcedureType();

        mockMvc.perform(get("/api/v1/procedure-types/{id}/availability", procedureType.getId()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.available").value(true));

        mockMvc.perform(patch("/api/v1/procedure-types/{id}/quota", procedureType.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"availableQuota\":11}"))
            .andExpect(status().isConflict());
    }

    @Test
    void returnsNotFoundAndRejectsInvalidCreateRequest() throws Exception {
        mockMvc.perform(get("/api/v1/procedure-types/{id}", UUID.randomUUID()))
            .andExpect(status().isNotFound());

        mockMvc.perform(post("/api/v1/procedure-types")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\" \",\"description\":\"\",\"dailyQuota\":-1}"))
            .andExpect(status().isBadRequest());
    }

    private ProcedureType saveProcedureType() {
        var procedureType = new ProcedureType();
        procedureType.setName("Licencia");
        procedureType.setDescription("Renovación");
        procedureType.setDailyQuota(10);
        procedureType.setAvailableQuota(4);
        return repository.saveAndFlush(procedureType);
    }
}
