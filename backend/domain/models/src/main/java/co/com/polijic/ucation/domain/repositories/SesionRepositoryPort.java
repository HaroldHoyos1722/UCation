package co.com.polijic.ucation.domain.repositories;

import co.com.polijic.ucation.domain.common.SesionModel;

public interface SesionRepositoryPort {

    SesionModel guardarSesion(SesionModel sesion);
    SesionModel findByCorreo(String correo);
    void deleteByCorreo(String correo);
}
