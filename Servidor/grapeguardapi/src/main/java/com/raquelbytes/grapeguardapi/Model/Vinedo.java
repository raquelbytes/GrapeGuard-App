package com.raquelbytes.grapeguardapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
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


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public LocalDate getFechaPlantacion() {
        return fechaPlantacion;
    }

    public void setFechaPlantacion(LocalDate fechaPlantacion) {
        this.fechaPlantacion = fechaPlantacion;
    }

    public BigDecimal getHectareas() {
        return hectareas;
    }

    public void setHectareas(BigDecimal hectareas) {
        this.hectareas = hectareas;
    }


}
