package co.com.polijic.ucation.postgresql.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "ESTUDIANTE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEstudiante;
    private Integer idPersona;
    private String semestreActual;
    private Integer estado;
}
