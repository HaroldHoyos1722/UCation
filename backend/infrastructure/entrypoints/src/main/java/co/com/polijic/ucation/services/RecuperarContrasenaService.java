package co.com.polijic.ucation.services;

import co.com.polijic.ucation.domain.exception.InfoException;
import co.com.polijic.ucation.domain.requesters.CambiarContrasenaRequester;
import co.com.polijic.ucation.domain.requesters.RecuperarContrasenaRequester;
import co.com.polijic.ucation.usecases.ports.RecuperarContrasenaPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecuperarContrasenaService {

    private final RecuperarContrasenaPort recuperarContrasenaPort;

    public RecuperarContrasenaService(RecuperarContrasenaPort recuperarContrasenaPort) {
        this.recuperarContrasenaPort = recuperarContrasenaPort;
    }

    @Transactional(rollbackFor = Exception.class)
    public void recuperarContrasena(RecuperarContrasenaRequester requester) throws InfoException {
        recuperarContrasenaPort.recuperarContrasena(requester);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cambiarContrasena(CambiarContrasenaRequester requester) throws Exception {
        recuperarContrasenaPort.cambiarContrasena(requester);
    }
}
