package co.com.polijic.ucation.usecases.adapters;

import co.com.polijic.ucation.domain.common.*;
import co.com.polijic.ucation.domain.enums.VariableEnum;
import co.com.polijic.ucation.domain.exception.InfoException;
import co.com.polijic.ucation.domain.repositories.DominioValorRepositoryPort;
import co.com.polijic.ucation.domain.repositories.PersonaRepositoryPort;
import co.com.polijic.ucation.domain.repositories.SesionRepositoryPort;
import co.com.polijic.ucation.domain.repositories.UsuarioRepositoryPort;
import co.com.polijic.ucation.domain.requesters.LoginRequester;
import co.com.polijic.ucation.domain.util.Encriptar;
import co.com.polijic.ucation.usecases.ports.LoginPort;
import co.com.polijic.ucation.usecases.util.JwtUtil;

import java.time.LocalDateTime;
import java.util.Objects;

import static co.com.polijic.ucation.domain.enums.MensajeEnum.USUARIO_CONTRASENA_INVALIDO;
import static co.com.polijic.ucation.domain.enums.MensajeEnum.USUARIO_NO_ACTIVO;

public class LoginAdapter implements LoginPort {

    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final PersonaRepositoryPort personaRepositoryPort;
    private final DominioValorRepositoryPort dominioValorRepositoryPort;
    private final SesionRepositoryPort sesionRepositoryPort;
    private final JwtUtil jwtUtil;

    public LoginAdapter(UsuarioRepositoryPort usuarioRepositoryPort,
                        PersonaRepositoryPort personaRepositoryPort,
                        DominioValorRepositoryPort dominioValorRepositoryPort,
                        SesionRepositoryPort sesionRepositoryPort,
                        JwtUtil jwtUtil) {
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.personaRepositoryPort = personaRepositoryPort;
        this.dominioValorRepositoryPort = dominioValorRepositoryPort;
        this.sesionRepositoryPort = sesionRepositoryPort;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public LoginModel login(LoginRequester requester) throws Exception {
        UsuarioModel usuario = usuarioRepositoryPort.consultarUsuarioPorCorreo(requester.getCorreo());

        if (Objects.isNull(usuario))
            throw new InfoException(USUARIO_CONTRASENA_INVALIDO.getValor());

        DominioValorModel dominioEstado = dominioValorRepositoryPort.consultarDominioPorId(usuario.getEstado());

        if (Objects.equals(dominioEstado.getValor(), VariableEnum.NO.getValor()))
            throw new InfoException(USUARIO_NO_ACTIVO.getValor());

        String contrasena = Encriptar.encriptar(requester.getContrasena());
        if (!Objects.equals(usuario.getContrasena(), contrasena))
            throw new InfoException(USUARIO_CONTRASENA_INVALIDO.getValor());

        PersonaModel persona = personaRepositoryPort.consultarPorId(usuario.getIdPersona());
        String token = jwtUtil.generarToken(requester.getCorreo());
        this.generarSesion(requester.getCorreo(), token);

        return LoginModel.builder()
                .nombre(persona.getNombre())
                .token(token)
                .build();
    }

    @Override
    public void logout(String token) {
        String correo = jwtUtil.validarToken(token).getSubject();
        sesionRepositoryPort.deleteByCorreo(correo);
    }

    private void generarSesion(String correo, String token) {
        SesionModel sesion = sesionRepositoryPort.findByCorreo(correo);

        if (Objects.nonNull(sesion)) {
            sesionRepositoryPort.deleteByCorreo(correo);
        }

        sesionRepositoryPort.guardarSesion(SesionModel.builder()
                .correo(correo)
                .tokenSesion(token)
                .fechaExpiracion(LocalDateTime.now().plusHours(1))
                .build());
    }
}
