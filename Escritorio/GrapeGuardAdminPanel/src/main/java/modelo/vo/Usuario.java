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
@Table(name = "Usuario")
public class Usuario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_usuario")
    private Integer id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Vinedo> vinedos = new ArrayList<>();

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Tratamiento> tratamientos = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nombre, String apellido, String email, String contrasena, byte[] foto) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasena = contrasena;
        this.foto = foto;
    }
}

