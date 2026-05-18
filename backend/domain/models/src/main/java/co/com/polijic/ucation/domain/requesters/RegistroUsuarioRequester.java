package co.com.polijic.ucation.domain.requesters;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistroUsuarioRequester implements Serializable {

    @NotNull
    private Integer tipoUsuario;
    @NotNull
    private String nombre;
    @NotNull
    private String apellido;
    @NotNull
    private Integer tipoIdentificacion;
    @NotNull
    private String numeroIdentificacion;
    @NotNull
    private String correo;
    @NotNull
    private String contrasena;
    private String celular;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Bogota")
    private LocalDate fechaNacimiento;
    @NotNull
    private Integer genero;
    private String direccion;
    private String ciudad;
    private String departamento;

    //Estudiante
    private String semestreActual;

    //Mentor
    private String nivelAcademico;
    private String descripcionMentor;
}
