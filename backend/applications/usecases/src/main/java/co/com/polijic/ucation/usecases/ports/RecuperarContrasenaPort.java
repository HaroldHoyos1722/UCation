package co.com.polijic.ucation.usecases.ports;

import co.com.polijic.ucation.domain.exception.InfoException;
import co.com.polijic.ucation.domain.requesters.CambiarContrasenaRequester;
import co.com.polijic.ucation.domain.requesters.RecuperarContrasenaRequester;

public interface RecuperarContrasenaPort {

    void recuperarContrasena(RecuperarContrasenaRequester requester) throws InfoException;
    void cambiarContrasena(CambiarContrasenaRequester requester) throws Exception;
}
