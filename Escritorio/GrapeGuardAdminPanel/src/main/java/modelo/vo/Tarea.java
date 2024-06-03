package modelo.vo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import lombok.*;

@Entity
@Table(name = "Tarea")
public class Tarea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_tarea")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID_vinedo")
    private Vinedo vinedo;

    @Column(name = "tarea", nullable = false)
    private String tarea;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado = Estado.Pendiente;

    @Column(name = "recordatorio")
    private Date recordatorio;

    @Column(name = "fecha_realizacion")
    private Date fechaRealizacion;

    public Tarea() {
    }

    public Tarea(Vinedo vinedo, String tarea, Estado estado, Date recordatorio, Date fechaRealizacion) {
        this.vinedo = vinedo;
        this.tarea = tarea;
        this.estado = estado;
        this.recordatorio = recordatorio;
        this.fechaRealizacion = fechaRealizacion;
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

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Date getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(Date recordatorio) {
        this.recordatorio = recordatorio;
    }

    public Date getFechaRealizacion() {
        return fechaRealizacion;
    }

    public void setFechaRealizacion(Date fechaRealizacion) {
        this.fechaRealizacion = fechaRealizacion;
    }

    public enum Estado {
        Pendiente,
        En_progreso,
        Completada
    }
}
