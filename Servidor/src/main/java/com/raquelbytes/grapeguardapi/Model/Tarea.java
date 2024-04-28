package com.raquelbytes.grapeguardapi.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Tarea")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_tarea")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_vinedo")
    private Vinedo vinedo;

    @Column(name = "tarea", nullable = false, length = 255)
    private String tarea;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoTarea estado;

    @Column(name = "recordatorio")
    private LocalDateTime recordatorio;

    @Column(name = "fecha_realizacion")
    private LocalDate fechaRealizacion;

    // Constructores, getters y setters
    public enum EstadoTarea {
        Pendiente,
        EnProgreso,
        Completada
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Vinedo getVinedo() {
        return vinedo;
    }

    public void setVinedo(Vinedo vinedo) {
        this.vinedo = vinedo;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
    }

    public LocalDateTime getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(LocalDateTime recordatorio) {
        this.recordatorio = recordatorio;
    }

    public LocalDate getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(LocalDate fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }
}

