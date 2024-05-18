package com.raquelbytes.grapeguardapi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
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


}
