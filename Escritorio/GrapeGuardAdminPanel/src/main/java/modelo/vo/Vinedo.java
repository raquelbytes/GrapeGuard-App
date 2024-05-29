package modelo.vo;

/**
 *
 * @author raquel
 */


import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Vinedo")
public class Vinedo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_vinedo")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ubicacion", nullable = false)
    private String ubicacion;

    @Column(name = "fecha_plantacion")
    private Date fechaPlantacion;

    @Column(name = "hectareas")
    private Double hectareas;

    @OneToMany(mappedBy = "vinedo", fetch = FetchType.LAZY)
    private List<Nota> notas = new ArrayList<>();

    @OneToMany(mappedBy = "vinedo", fetch = FetchType.LAZY)
    private List<VinedoTratamiento> tratamientos = new ArrayList<>();

    public Vinedo() {
    }

    public Vinedo(Usuario usuario, String nombre, String ubicacion, Date fechaPlantacion, Double hectareas) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fechaPlantacion = fechaPlantacion;
        this.hectareas = hectareas;
    }
}


