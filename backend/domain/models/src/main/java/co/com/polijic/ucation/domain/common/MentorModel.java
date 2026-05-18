package co.com.polijic.ucation.domain.common;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MentorModel implements Serializable {

    private Integer idMentor;
    private Integer idPersona;
    private Double calificacion;
    private String descripcion;
    private String nivelAcademico;
    private Integer estado;
}
