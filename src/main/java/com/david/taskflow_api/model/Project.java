package com.david.taskflow_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, length = 40)
    private String nombre;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)//NO Queremos proyectos sin usuarios(optional=false)
    @JoinColumn(name = "user_id", nullable = false)//La FK no puede ser null(nullable=false)
    private User owner;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist //Para que se ponga la fecha automáticamente al crear la entidad
    public void prePersist() {

        this.createdAt = LocalDateTime.now();
    }
}
