package com.raquelbytes.grapeguardapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
        import java.time.LocalDate;

@Entity
@Table(name = "Vinedo_Tratamiento")
public class VinedoTratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_vinedo_tratamiento")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_vinedo")
    @JsonIgnore
    private Vinedo vinedo;

    @ManyToOne
    @JoinColumn(name = "ID_tratamiento")
    @JsonIgnore
    private Tratamiento tratamiento;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "fecha_compra")
    private LocalDate fechaCompra;

    @Column(name = "en_posesion", nullable = false)
    private boolean enPosesion;


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

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public boolean isEnPosesion() {
        return enPosesion;
    }

    public void setEnPosesion(boolean enPosesion) {
        this.enPosesion = enPosesion;
    }
}
