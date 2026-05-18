package co.com.polijic.ucation.postgresql.adapters;

import co.com.polijic.ucation.domain.common.PersonaModel;
import co.com.polijic.ucation.domain.repositories.PersonaRepositoryPort;
import co.com.polijic.ucation.domain.util.Mapper;
import co.com.polijic.ucation.postgresql.entities.PersonaEntity;
import co.com.polijic.ucation.postgresql.repository.PersonaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PersonaBdAdapter implements PersonaRepositoryPort {

    private final PersonaRepository personaRepository;

    public PersonaBdAdapter(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public PersonaModel consultarPorId(Integer id) {
        Optional<PersonaEntity> entity = personaRepository.findById(id);
        return entity.map(p -> Mapper.map(p, PersonaModel.class)).orElse(null);
    }

    @Override
    public PersonaModel guardarPersona(PersonaModel persona) {
        PersonaEntity entity = Mapper.map(persona, PersonaEntity.class);
        return Mapper.map(personaRepository.save(entity), PersonaModel.class);
    }

    @Override
    public PersonaModel consultarPorTipoIdentificacionYNumeroIdentificacion(Integer tipoIdentificacion, String numeroIdentificacion) {
        PersonaEntity entity = personaRepository
                .findByTipoIdentificacionAndNumeroIdentificacion(tipoIdentificacion, numeroIdentificacion);
        return (Objects.nonNull(entity)) ? Mapper.map(entity, PersonaModel.class) : null;
    }
}
