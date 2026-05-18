package co.com.polijic.ucation.postgresql.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEntity implements Serializable {

    @Id
    private String correo;
    private String contrasena;
    private Integer idPersona;
    private byte[] fotoPerfil;
    private Integer estado;
}
