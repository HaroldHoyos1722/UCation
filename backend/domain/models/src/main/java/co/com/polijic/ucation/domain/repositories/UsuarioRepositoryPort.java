package co.com.polijic.ucation.domain.repositories;

import co.com.polijic.ucation.domain.common.UsuarioModel;

public interface UsuarioRepositoryPort {

    UsuarioModel guardarUsuario(UsuarioModel usuario);
    UsuarioModel consultarUsuarioPorCorreo(String correo);
}
