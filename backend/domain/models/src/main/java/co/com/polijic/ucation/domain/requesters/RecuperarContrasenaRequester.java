package co.com.polijic.ucation.domain.requesters;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecuperarContrasenaRequester implements Serializable {

    private String correo;
}
