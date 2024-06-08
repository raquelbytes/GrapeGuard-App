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

    @OneToMany(mappedBy = "vinedo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Nota> notas = new ArrayList<>();

    @OneToMany(mappedBy = "vinedo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<VinedoTratamiento> tratamientos = new ArrayList<>();
    
    @OneToMany(mappedBy = "vinedo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cosecha> cosechas = new ArrayList<>();

    @OneToMany(mappedBy = "vinedo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tarea> tareas = new ArrayList<>();

    public Vinedo() {
    }

    public Vinedo(Usuario usuario, String nombre, String ubicacion, Date fechaPlantacion, Double hectareas) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.fechaPlantacion = fechaPlantacion;
        this.hectareas = hectareas;
    }

    @Override
    public String toString() {
        return nombre ;
    }

    public Integer getId() {
        return id;
    }

    public List<Cosecha> getCosechas() {
        return cosechas;
    }

    public void setCosechas(List<Cosecha> cosechas) {
        this.cosechas = cosechas;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Date getFechaPlantacion() {
        return fechaPlantacion;
    }

    public void setFechaPlantacion(Date fechaPlantacion) {
        this.fechaPlantacion = fechaPlantacion;
    }

    public Double getHectareas() {
        return hectareas;
    }

    public void setHectareas(Double hectareas) {
        this.hectareas = hectareas;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public List<VinedoTratamiento> getTratamientos() {
        return tratamientos;
    }

    public void setTratamientos(List<VinedoTratamiento> tratamientos) {
        this.tratamientos = tratamientos;
    }
   

   
    
    
}


