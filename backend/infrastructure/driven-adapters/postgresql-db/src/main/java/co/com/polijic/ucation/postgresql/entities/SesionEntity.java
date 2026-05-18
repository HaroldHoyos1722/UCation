package co.com.polijic.ucation.postgresql.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "sesion")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SesionEntity implements Serializable {

    @Id
    private String correo;
    private String tokenSesion;
    private LocalDateTime fechaExpiracion;
}