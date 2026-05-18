package co.com.polijic.ucation.usecases.ports;

import co.com.polijic.ucation.domain.requesters.RegistroUsuarioRequester;

public interface UsuarioPort {

    void registrarUsuario(RegistroUsuarioRequester requester) throws Exception;
}
