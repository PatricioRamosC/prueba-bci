package cl.nttdata.prueba_bci.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

/**
 * Entidad JPA que representa un teléfono asociado a un usuario.
 * Contiene número telefónico y códigos de país y ciudad.
 * 
 * @author Patricio Ramos - NTTDATA
 * @since 2025-01-01
 * @version 1.0
 */
@Entity
@Data
@Table(name = "telefonos")
public class TelefonoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_telefono")
    private Long id;

    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    @Column(name = "codigo_ciudad", nullable = false, length = 10)
    private String codigoCiudad;

    @Column(name = "codigo_pais", nullable = false, length = 10)
    private String codigoPais;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioModel usuario;

    @Override
    public String toString() {
        return "TelefonoModel [id=" + id + ", numero=" + numero + ", codigoCiudad=" + codigoCiudad + ", codigoPais="
                + codigoPais + "]";
    }
}
