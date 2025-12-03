package cl.nttdata.prueba_bci.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(
    name = "usuarios",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_usuario_correo", columnNames = "correo")
    }
)
public class UsuarioModel {

    @Id
    @GeneratedValue(generator = "UUID")
    @org.hibernate.annotations.GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correo", nullable = false, length = 150, unique = true)
    private String correo;

    @Column(name = "contrasena", nullable = false)
    private String contrasena;

    @OneToMany(
        mappedBy = "usuario",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    private List<TelefonoModel> telefonos;

    @Column(name = "creado", nullable = false)
    private LocalDateTime creado;

    @Column(name = "modificado")
    private LocalDateTime modificado;

    @Column(name = "ultimo_login")
    private LocalDateTime ultimoLogin;

    @Column(name = "token", length = 500)
    private String token;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

	@Override
	public String toString() {
		return "UsuarioModel [id=" + id + ", nombre=" + nombre + ", correo=" + correo + ", contrasena=" + contrasena
				+ ", telefonos=" + telefonos + ", creado=" + creado + ", modificado=" + modificado + ", ultimoLogin="
				+ ultimoLogin + ", token=" + token + ", activo=" + activo + "]";
	}

}
