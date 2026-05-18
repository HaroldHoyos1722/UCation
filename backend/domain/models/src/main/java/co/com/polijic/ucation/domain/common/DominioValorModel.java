package co.com.polijic.ucation.domain.common;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DominioValorModel implements Serializable {

    private Integer idDominio;
    private String nombreDominio;
    private String valor;
    private String codigo;
    private String activo;
}
