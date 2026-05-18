package co.com.polijic.ucation.postgresql.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "MENTOR")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMentor;
    private Integer idPersona;
    private Double calificacion;
    private String nivelAcademico;
    private Integer estado;
}
