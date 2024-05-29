
package modelo.vo;

/**
 *
 * @author raquel
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "Vinedo_Tratamiento")
public class VinedoTratamiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_vinedo_tratamiento")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_vinedo", nullable = false)
    private Vinedo vinedo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_tratamiento", nullable = false)
    private Tratamiento tratamiento;

    @Column(name = "cantidad", nullable = false)
    private int cantidad;

    @Column(name = "fecha_compra")
    private Date fechaCompra;

    @Column(name = "en_posesion", nullable = false)
    private boolean enPosesion;

    public VinedoTratamiento() {
    }

    public VinedoTratamiento(Vinedo vinedo, Tratamiento tratamiento, int cantidad, Date fechaCompra, boolean enPosesion) {
        this.vinedo = vinedo;
        this.tratamiento = tratamiento;
        this.cantidad = cantidad;
        this.fechaCompra = fechaCompra;
        this.enPosesion = enPosesion;
    }
}

