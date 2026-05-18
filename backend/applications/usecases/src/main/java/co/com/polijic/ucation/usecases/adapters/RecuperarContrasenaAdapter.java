package co.com.polijic.ucation.usecases.adapters;

import co.com.polijic.ucation.domain.common.DominioValorModel;
import co.com.polijic.ucation.domain.common.RecuperarContrasenaModel;
import co.com.polijic.ucation.domain.common.UsuarioModel;
import co.com.polijic.ucation.domain.enums.CorreoEnum;
import co.com.polijic.ucation.domain.enums.VariableEnum;
import co.com.polijic.ucation.domain.exception.InfoException;
import co.com.polijic.ucation.domain.repositories.DominioValorRepositoryPort;
import co.com.polijic.ucation.domain.repositories.EmailRepositoryPort;
import co.com.polijic.ucation.domain.repositories.RecuperarContrasenaRepositoryPort;
import co.com.polijic.ucation.domain.repositories.UsuarioRepositoryPort;
import co.com.polijic.ucation.domain.requesters.CambiarContrasenaRequester;
import co.com.polijic.ucation.domain.requesters.RecuperarContrasenaRequester;
import co.com.polijic.ucation.domain.util.Encriptar;
import co.com.polijic.ucation.usecases.ports.RecuperarContrasenaPort;
import org.springframework.beans.factory.annotation.Value;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;

import static co.com.polijic.ucation.domain.enums.MensajeEnum.*;

public class RecuperarContrasenaAdapter implements RecuperarContrasenaPort {

    @Value("${tiempo.recuperarContrasena}")
    private Integer tiempoCodigo;

    private final RecuperarContrasenaRepositoryPort recuperarContrasenaRepositoryPort;
    private final DominioValorRepositoryPort dominioValorRepositoryPort;
    private final UsuarioRepositoryPort usuarioRepositoryPort;
    private final EmailRepositoryPort emailRepositoryPort;

    public RecuperarContrasenaAdapter(RecuperarContrasenaRepositoryPort recuperarContrasenaRepositoryPort,
                                      DominioValorRepositoryPort dominioValorRepositoryPort,
                                      UsuarioRepositoryPort usuarioRepositoryPort,
                                      EmailRepositoryPort emailRepositoryPort) {
        this.recuperarContrasenaRepositoryPort = recuperarContrasenaRepositoryPort;
        this.dominioValorRepositoryPort = dominioValorRepositoryPort;
        this.usuarioRepositoryPort = usuarioRepositoryPort;
        this.emailRepositoryPort = emailRepositoryPort;
    }

    @Override
    public void recuperarContrasena(RecuperarContrasenaRequester requester) throws InfoException {
        UsuarioModel usuario = usuarioRepositoryPort.consultarUsuarioPorCorreo(requester.getCorreo());

        if (Objects.isNull(usuario)) return;

        RecuperarContrasenaModel codigoVigente = recuperarContrasenaRepositoryPort
                .consultarCodigosPorCorreo(requester.getCorreo(), LocalDateTime.now());

        if (Objects.isNull(codigoVigente)) {
            codigoVigente = RecuperarContrasenaModel.builder()
                    .correo(requester.getCorreo())
                    .codigo(generarCodigo())
                    .fechaInicio(LocalDateTime.now())
                    .fechaFin(LocalDateTime.now().plusMinutes(tiempoCodigo)).build();
            codigoVigente = recuperarContrasenaRepositoryPort.guardarCodigo(codigoVigente);
        }

        String plantilla = CorreoEnum.PLANTILLA_RECUERAR_CONTRASENA.getContenido()
                .replace("{{CODIGO}}", codigoVigente.getCodigo())
                .replace("{{TIEMPO_RECUERAR_CONTRASENA}}", tiempoCodigo.toString());

        try {
            emailRepositoryPort.enviarEmail(requester.getCorreo(), "Recuperar contraseña", plantilla);
        } catch (Exception e) {
            throw new InfoException("Error al enviar el correo de recuperación de contraseña");
        }
    }

    @Override
    public void cambiarContrasena(CambiarContrasenaRequester requester) throws Exception {
        UsuarioModel usuario = usuarioRepositoryPort.consultarUsuarioPorCorreo(requester.getCorreo());

        if (Objects.isNull(usuario))
            throw new InfoException(MENSAJE_CAMBIO_CONTRASENA_ERROR.getValor());

        RecuperarContrasenaModel codigoVigente = recuperarContrasenaRepositoryPort
                .consultarCodigosPorCorreo(requester.getCorreo(), LocalDateTime.now());

        if (Objects.isNull(codigoVigente))
            throw new InfoException(CODIGO_RECUPERAR_CONTRASENA_VENCIDO.getValor());

        if (!Objects.equals(codigoVigente.getCodigo(), requester.getCodigo()))
            throw new InfoException(CODIGO_RECUPERACION_ERRONEO.getValor());

        DominioValorModel dominioEstadoActivo = dominioValorRepositoryPort
                .consultarDominioValorPorNombreDominioAndCodigo(VariableEnum.ESTADO.getValor(), VariableEnum.SI.getValor());

        usuario.setContrasena(Encriptar.encriptar(requester.getContrasena()));
        usuario.setEstado(dominioEstadoActivo.getIdDominio());
        usuarioRepositoryPort.guardarUsuario(usuario);
    }

    private String generarCodigo() {
        SecureRandom random = new SecureRandom();
        int codigo = random.nextInt(900000) + 100000;
        return String.valueOf(codigo);
    }
}
