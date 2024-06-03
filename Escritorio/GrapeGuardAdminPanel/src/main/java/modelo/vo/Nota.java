package modelo.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "Nota")
public class Nota implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_nota")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_vinedo", nullable = false)
    private Vinedo vinedo;

    @Column(name = "nota", nullable = false)
    private String nota;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad")
    private PrioridadNota prioridad;

    public enum PrioridadNota {
        Alta,
        Media,
        Baja
    }

    public Nota() {
    }

    public Nota(Vinedo vinedo, String nota, PrioridadNota prioridad) {
        this.vinedo = vinedo;
        this.nota = nota;
        this.fechaCreacion = LocalDateTime.now();
        this.prioridad = prioridad;
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

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public PrioridadNota getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadNota prioridad) {
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "Nota{" + "id=" + id + ", vinedo=" + vinedo + ", nota=" + nota + ", fechaCreacion=" + fechaCreacion + ", prioridad=" + prioridad + '}';
    }
    
    
}
