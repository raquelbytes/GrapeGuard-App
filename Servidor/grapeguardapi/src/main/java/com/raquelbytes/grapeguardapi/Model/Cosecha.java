package com.raquelbytes.grapeguardapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Cosecha")
public class Cosecha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_cosecha")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_vinedo", nullable = false)
    @JsonIgnore
    private Vinedo vinedo;

    @Column(name = "nombre_variedad", nullable = false)
    private String nombreVariedad;

    @Column(name = "cantidad_uvas", nullable = false)
    private BigDecimal cantidadUvas;

    @Column(name = "precio_venta_kg", nullable = false)
    private BigDecimal precioVentaKg;

    @Column(name = "fecha_cosecha", nullable = false)
    private LocalDate fechaCosecha;


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

    public String getNombreVariedad() {
        return nombreVariedad;
    }

    public void setNombreVariedad(String nombreVariedad) {
        this.nombreVariedad = nombreVariedad;
    }

    public BigDecimal getCantidadUvas() {
        return cantidadUvas;
    }

    public void setCantidadUvas(BigDecimal cantidadUvas) {
        this.cantidadUvas = cantidadUvas;
    }

    public BigDecimal getPrecioVentaKg() {
        return precioVentaKg;
    }

    public void setPrecioVentaKg(BigDecimal precioVentaKg) {
        this.precioVentaKg = precioVentaKg;
    }

    public LocalDate getFechaCosecha() {
        return fechaCosecha;
    }

    public void setFechaCosecha(LocalDate fechaCosecha) {
        this.fechaCosecha = fechaCosecha;
    }
}
