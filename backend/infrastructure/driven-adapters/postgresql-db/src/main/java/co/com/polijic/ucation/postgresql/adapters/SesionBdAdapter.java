package co.com.polijic.ucation.postgresql.adapters;

import co.com.polijic.ucation.domain.common.SesionModel;
import co.com.polijic.ucation.domain.repositories.SesionRepositoryPort;
import co.com.polijic.ucation.domain.util.Mapper;
import co.com.polijic.ucation.postgresql.entities.SesionEntity;
import co.com.polijic.ucation.postgresql.repository.SesionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SesionBdAdapter implements SesionRepositoryPort {

    private final SesionRepository sesionRepository;

    public SesionBdAdapter(SesionRepository sesionRepository) {
        this.sesionRepository = sesionRepository;
    }

    @Override
    public SesionModel guardarSesion(SesionModel sesion) {
        SesionEntity entity = Mapper.map(sesion, SesionEntity.class);
        return Mapper.map(sesionRepository.save(entity), SesionModel.class);
    }

    @Override
    public SesionModel findByCorreo(String correo) {
        Optional<SesionEntity> entity = sesionRepository.findById(correo);
        return entity.map(s -> Mapper.map(s, SesionModel.class)).orElse(null);
    }

    @Override
    public void deleteByCorreo(String correo) {
        sesionRepository.deleteById(correo);
    }
}
