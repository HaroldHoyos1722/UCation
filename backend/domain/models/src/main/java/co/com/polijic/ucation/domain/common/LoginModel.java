package co.com.polijic.ucation.domain.common;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginModel implements Serializable {

    private String token;
    private String nombre;
}
