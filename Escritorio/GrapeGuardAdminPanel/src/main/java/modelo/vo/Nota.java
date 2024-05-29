package modelo.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.*;

@Data
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
}
