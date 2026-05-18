package co.com.polijic.ucation.postgresql.adapters;

import co.com.polijic.ucation.domain.common.EstudianteModel;
import co.com.polijic.ucation.domain.repositories.EstudianteRepositoryPort;
import co.com.polijic.ucation.domain.util.Mapper;
import co.com.polijic.ucation.postgresql.entities.EstudianteEntity;
import co.com.polijic.ucation.postgresql.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

@Service
public class EstudianteBdAdapter implements EstudianteRepositoryPort {

    private final EstudianteRepository estudianteRepository;

    public EstudianteBdAdapter(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public EstudianteModel guardarEstudiante(EstudianteModel estudiante) {
        EstudianteEntity entity = Mapper.map(estudiante, EstudianteEntity.class);
        return Mapper.map(estudianteRepository.save(entity), EstudianteModel.class);
    }
}
