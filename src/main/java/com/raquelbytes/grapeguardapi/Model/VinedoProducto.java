package com.raquelbytes.grapeguardapi.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "Vinedo_Producto")
public class VinedoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_vinedo_producto")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_vinedo", nullable = false)
    private Vinedo vinedo;

    @ManyToOne
    @JoinColumn(name = "ID_producto", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    // Getters y setters

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

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}

