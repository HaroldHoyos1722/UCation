package co.com.polijic.ucation.domain.common;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class GeneralResponse<T> {

    private String codigo;
    private String tipoMensaje;
    private String mensaje;
    private T respuesta;
}
