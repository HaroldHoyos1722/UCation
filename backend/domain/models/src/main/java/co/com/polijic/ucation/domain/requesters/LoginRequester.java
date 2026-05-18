package co.com.polijic.ucation.domain.requesters;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequester implements Serializable {

    @NotNull
    private String correo;
    @NotNull
    private String contrasena;
}
