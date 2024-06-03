package modelo.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.*;

@Entity
@Table(name = "Cosecha")
public class Cosecha implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_cosecha")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_vinedo")
    private Vinedo vinedo;

    @Column(name = "nombre_variedad", nullable = false)
    private String nombreVariedad;

    @Column(name = "cantidad_uvas", nullable = false)
    private Double cantidadUvas;

    @Column(name = "precio_venta_kg", nullable = false)
    private Double precioVentaKg;

    @Column(name = "fecha_cosecha")
    private Date fechaCosecha;

    public Cosecha() {
    }

    public Cosecha(Vinedo vinedo, String nombreVariedad, Double cantidadUvas, Double precioVentaKg, Date fechaCosecha) {
        this.vinedo = vinedo;
        this.nombreVariedad = nombreVariedad;
        this.cantidadUvas = cantidadUvas;
        this.precioVentaKg = precioVentaKg;
        this.fechaCosecha = fechaCosecha;
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

    public String getNombreVariedad() {
        return nombreVariedad;
    }

    public void setNombreVariedad(String nombreVariedad) {
        this.nombreVariedad = nombreVariedad;
    }

    public Double getCantidadUvas() {
        return cantidadUvas;
    }

    public void setCantidadUvas(Double cantidadUvas) {
        this.cantidadUvas = cantidadUvas;
    }

    public Double getPrecioVentaKg() {
        return precioVentaKg;
    }

    public void setPrecioVentaKg(Double precioVentaKg) {
        this.precioVentaKg = precioVentaKg;
    }

    public Date getFechaCosecha() {
        return fechaCosecha;
    }

    public void setFechaCosecha(Date fechaCosecha) {
        this.fechaCosecha = fechaCosecha;
    }
}
