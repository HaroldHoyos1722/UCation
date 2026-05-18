package co.com.polijic.ucation.postgresql.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "persona")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPersona;
    private String nombre;
    private String apellido;
    private Integer tipoIdentificacion;
    private String numeroIdentificacion;
    private String correo;
    private String celular;
    private LocalDate fechaNacimiento;
    private Integer genero;
    private String direccion;
    private String ciudad;
    private String departamento;
    private LocalDate fechaRegistro;
}
