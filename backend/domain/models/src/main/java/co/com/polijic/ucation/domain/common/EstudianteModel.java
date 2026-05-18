package co.com.polijic.ucation.domain.common;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteModel implements Serializable {

    private Integer idEstudiante;
    private Integer idPersona;
    private String semestreActual;
    private Integer estado;
}
