package com.raquelbytes.grapeguardapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "Vinedo")
public class Vinedo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_vinedo")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_usuario", nullable = false)
    @JsonIgnore
    private Usuario usuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ubicacion", nullable = false)
    private String ubicacion;

    @Column(name = "fecha_plantacion")
    private LocalDate fechaPlantacion;

    @Column(name = "hectareas")
    private BigDecimal hectareas;

    @OneToMany(mappedBy = "vinedo")
    private List<Cosecha> cosechas;

    @OneToMany(mappedBy = "vinedo")
    private List<Nota> notas;

    @OneToMany(mappedBy = "vinedo")
    private List<Tarea> tareas;



}
