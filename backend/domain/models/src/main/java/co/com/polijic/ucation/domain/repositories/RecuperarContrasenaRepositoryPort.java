package co.com.polijic.ucation.domain.repositories;

import co.com.polijic.ucation.domain.common.RecuperarContrasenaModel;

import java.time.LocalDateTime;

public interface RecuperarContrasenaRepositoryPort {

    RecuperarContrasenaModel guardarCodigo(RecuperarContrasenaModel codigoRecuperar);

    /**
     * Consulta los códigos vigentes de recuperación de contraseña por correo electronico.
     *
     * @param correo Correo electronico del usuario a validar
     * @param fechaActual Fecha actual
     * @return Codigo vigente
     */
    RecuperarContrasenaModel consultarCodigosPorCorreo(String correo, LocalDateTime fechaActual);
}
