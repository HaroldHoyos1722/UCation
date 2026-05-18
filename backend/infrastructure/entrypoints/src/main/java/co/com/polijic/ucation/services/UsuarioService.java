package co.com.polijic.ucation.services;

import co.com.polijic.ucation.domain.requesters.RegistroUsuarioRequester;
import co.com.polijic.ucation.usecases.ports.UsuarioPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioPort usuarioPort;

    public UsuarioService(UsuarioPort usuarioPort) {
        this.usuarioPort = usuarioPort;
    }

    @Transactional(rollbackFor = Exception.class)
    public void registrarUsuario(RegistroUsuarioRequester requester) throws Exception {
        usuarioPort.registrarUsuario(requester);
    }
}
