package co.com.polijic.ucation.domain.common;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SesionModel implements Serializable {

    private String correo;
    private String tokenSesion;
    private LocalDateTime fechaExpiracion;
}
