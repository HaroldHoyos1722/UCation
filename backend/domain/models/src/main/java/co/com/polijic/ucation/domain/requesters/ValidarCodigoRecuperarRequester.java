package co.com.polijic.ucation.domain.requesters;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidarCodigoRecuperarRequester extends RecuperarContrasenaRequester implements Serializable {

    private String codigoVerificacion;
    private String nuevaContrasena;
}
