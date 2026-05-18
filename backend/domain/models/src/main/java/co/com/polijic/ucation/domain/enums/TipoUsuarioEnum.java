package co.com.polijic.ucation.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoUsuarioEnum {
    ESTUDIANTE("ESTUDIANTE"),
    MENTOR("MENTOR");

    private String valor;
}
