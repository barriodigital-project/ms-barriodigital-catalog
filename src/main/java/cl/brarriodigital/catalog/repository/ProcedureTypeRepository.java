package cl.brarriodigital.catalog.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.brarriodigital.catalog.model.ProcedureType;

public interface ProcedureTypeRepository extends JpaRepository<ProcedureType, UUID> {

    List<ProcedureType> findAllByOrderByNameAscIdAsc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select procedureType from ProcedureType procedureType where procedureType.id = :id")
    Optional<ProcedureType> findByIdForUpdate(@Param("id") UUID id);
}
