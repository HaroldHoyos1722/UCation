package co.com.polijic.ucation.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoMensajeEnum {
    OK("OK"),
    ERROR("ERROR"),
    DETIENE("DETIENE"),
    ALERTA("ALERTA");

    private final String valor;
}
