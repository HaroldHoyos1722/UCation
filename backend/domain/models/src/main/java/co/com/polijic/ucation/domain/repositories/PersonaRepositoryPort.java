package co.com.polijic.ucation.domain.repositories;

import co.com.polijic.ucation.domain.common.PersonaModel;

public interface PersonaRepositoryPort {

    PersonaModel consultarPorId(Integer id);
    PersonaModel guardarPersona(PersonaModel persona);
    PersonaModel consultarPorTipoIdentificacionYNumeroIdentificacion(Integer tipoIdentificacion, String numeroIdentificacion);
}
