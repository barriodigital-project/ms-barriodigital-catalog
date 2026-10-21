package cl.brarriodigital.catalog.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "procedure_types")
@Getter
@Setter
@NoArgsConstructor
public class ProcedureType {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(name = "daily_quota", nullable = false)
    private int dailyQuota;

    @Column(name = "available_quota", nullable = false)
    private int availableQuota;

    @ElementCollection
    @CollectionTable(
        name = "procedure_type_requirements",
        joinColumns = @JoinColumn(name = "procedure_type_id")
    )
    @OrderColumn(name = "requirement_order")
    @Column(name = "requirement", nullable = false, length = 500)
    private List<String> requirements = new ArrayList<>();

    @Column(nullable = false)
    private boolean active = true;
}
