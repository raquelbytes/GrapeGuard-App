package modelo.vo;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "Vinedo_Tratamiento")
public class VinedoTratamiento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_vinedo_tratamiento")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ID_vinedo", nullable = false)
    private Vinedo vinedo;

    @ManyToOne
    @JoinColumn(name = "ID_tratamiento", nullable = false)
    private Tratamiento tratamiento;

    public VinedoTratamiento() {
    }

    public VinedoTratamiento(Vinedo vinedo, Tratamiento tratamiento) {
        this.vinedo = vinedo;
        this.tratamiento = tratamiento;
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

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }
}
