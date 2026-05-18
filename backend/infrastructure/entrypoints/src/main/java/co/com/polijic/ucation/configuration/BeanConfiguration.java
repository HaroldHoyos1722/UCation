package co.com.polijic.ucation.configuration;

import co.com.polijic.ucation.domain.repositories.*;
import co.com.polijic.ucation.usecases.adapters.DominioAdapter;
import co.com.polijic.ucation.usecases.adapters.LoginAdapter;
import co.com.polijic.ucation.usecases.adapters.RecuperarContrasenaAdapter;
import co.com.polijic.ucation.usecases.adapters.UsuarioAdapter;
import co.com.polijic.ucation.usecases.ports.DominioPort;
import co.com.polijic.ucation.usecases.ports.LoginPort;
import co.com.polijic.ucation.usecases.ports.RecuperarContrasenaPort;
import co.com.polijic.ucation.usecases.ports.UsuarioPort;
import co.com.polijic.ucation.usecases.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public DominioPort dominioPort(DominioValorRepositoryPort dominioValorRepositoryPort) {
        return new DominioAdapter(dominioValorRepositoryPort);
    }

    @Bean
    public UsuarioPort usuarioPort(PersonaRepositoryPort personaRepositoryPort,
                                   UsuarioRepositoryPort usuarioRepositoryPort,
                                   DominioValorRepositoryPort dominioValorRepositoryPort,
                                   EstudianteRepositoryPort estudianteRepositoryPort,
                                   MentorRepositoryPort mentorRepositoryPort) {
        return new UsuarioAdapter(personaRepositoryPort, usuarioRepositoryPort, dominioValorRepositoryPort, estudianteRepositoryPort,
                mentorRepositoryPort);
    }

    @Bean
    public RecuperarContrasenaPort recuperarContrasenaPort(RecuperarContrasenaRepositoryPort recuperarContrasenaRepositoryPort,
                                                           DominioValorRepositoryPort dominioValorRepositoryPort,
                                                           UsuarioRepositoryPort usuarioRepositoryPort,
                                                           EmailRepositoryPort emailRepositoryPort) {
        return new RecuperarContrasenaAdapter(recuperarContrasenaRepositoryPort, dominioValorRepositoryPort, usuarioRepositoryPort,
                emailRepositoryPort);
    }

    @Bean
    public LoginPort loginPort(UsuarioRepositoryPort usuarioRepositoryPort,
                               PersonaRepositoryPort personaRepositoryPort,
                               DominioValorRepositoryPort dominioValorRepositoryPort,
                               SesionRepositoryPort sesionRepositoryPort,
                               JwtUtil jwtUtil) {
        return new LoginAdapter(usuarioRepositoryPort, personaRepositoryPort, dominioValorRepositoryPort,
                sesionRepositoryPort, jwtUtil);
    }
}
