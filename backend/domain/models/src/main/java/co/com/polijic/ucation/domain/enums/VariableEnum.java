package co.com.polijic.ucation.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VariableEnum {
    SI("SI"),
    NO("NO"),
    ESTADO("ESTADO");

    private String valor;
}
