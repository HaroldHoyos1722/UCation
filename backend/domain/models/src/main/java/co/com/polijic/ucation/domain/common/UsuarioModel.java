package co.com.polijic.ucation.domain.common;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioModel implements Serializable {

    private String correo;
    private String contrasena;
    private Integer idPersona;
    private byte[] fotoPerfil;
    private Integer estado;
}
