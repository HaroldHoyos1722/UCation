package co.com.polijic.ucation.domain.common;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecuperarContrasenaModel implements Serializable {

    private Long id;
    private String correo;
    private String codigo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
