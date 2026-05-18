package co.com.polijic.ucation.domain.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonaModel implements Serializable {

    private Integer idPersona;
    private String nombre;
    private String apellido;
    private Integer tipoIdentificacion;
    private String numeroIdentificacion;
    private String correo;
    private String celular;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Bogota")
    private LocalDate fechaNacimiento;
    private Integer genero;
    private String direccion;
    private String ciudad;
    private String departamento;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "America/Bogota")
    private LocalDate fechaRegistro;
}
