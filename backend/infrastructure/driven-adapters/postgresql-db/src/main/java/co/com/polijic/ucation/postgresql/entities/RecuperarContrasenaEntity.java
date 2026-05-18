package co.com.polijic.ucation.postgresql.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "recuperar_contrasena")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecuperarContrasenaEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String correo;
    private String codigo;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
}
