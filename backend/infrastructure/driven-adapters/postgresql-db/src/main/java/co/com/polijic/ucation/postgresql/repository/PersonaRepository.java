package co.com.polijic.ucation.postgresql.repository;

import co.com.polijic.ucation.postgresql.entities.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, Integer> {

    PersonaEntity findByTipoIdentificacionAndNumeroIdentificacion(Integer tipoIdentificacion, String numeroIdentificacion);
}
