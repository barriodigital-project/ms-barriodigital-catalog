package cl.brarriodigital.catalog.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import cl.brarriodigital.catalog.model.ProcedureType;

@DataJpaTest
class ProcedureTypeRepositoryTest {

    @Autowired
    private ProcedureTypeRepository repository;

    @Test
    void persistsProcedureTypeAndOrderedRequirements() {
        var procedureType = new ProcedureType();
        procedureType.setName("Licencia");
        procedureType.setDescription("Renovación de licencia");
        procedureType.setDailyQuota(10);
        procedureType.setAvailableQuota(10);
        procedureType.setRequirements(List.of("Cédula", "Licencia anterior"));

        var saved = repository.saveAndFlush(procedureType);
        var loaded = repository.findById(saved.getId()).orElseThrow();

        assertThat(loaded.getAvailableQuota()).isEqualTo(10);
        assertThat(loaded.getRequirements()).containsExactly("Cédula", "Licencia anterior");
    }
}
