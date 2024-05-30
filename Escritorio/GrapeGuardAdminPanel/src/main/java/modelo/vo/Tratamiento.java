
package modelo.vo;

/**
 *
 * @author raquel
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Tratamiento")
public class Tratamiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_tratamiento")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "precio_unitario", nullable = false)
    private double precioUnitario;

    @Column(name = "en_posesion", nullable = false)
    private boolean enPosesion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_usuario", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "tratamiento", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<VinedoTratamiento> vinedoTratamientos = new ArrayList<>();

    public Tratamiento() {
    }

    public Tratamiento(String nombre, int cantidad, double precioUnitario, boolean enPosesion, Usuario usuario) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.enPosesion = enPosesion;
        this.usuario = usuario;
    }
}
