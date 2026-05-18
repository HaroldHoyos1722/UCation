package co.com.polijic.ucation.postgresql.adapters;

import co.com.polijic.ucation.domain.common.RecuperarContrasenaModel;
import co.com.polijic.ucation.domain.repositories.RecuperarContrasenaRepositoryPort;
import co.com.polijic.ucation.domain.util.Mapper;
import co.com.polijic.ucation.postgresql.entities.RecuperarContrasenaEntity;
import co.com.polijic.ucation.postgresql.repository.RecuperarContrasenaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class RecuperarContrasenaBdAdapter implements RecuperarContrasenaRepositoryPort {

    private final RecuperarContrasenaRepository recuperarContrasenaRepository;

    public RecuperarContrasenaBdAdapter(RecuperarContrasenaRepository recuperarContrasenaRepository) {
        this.recuperarContrasenaRepository = recuperarContrasenaRepository;
    }

    @Override
    public RecuperarContrasenaModel guardarCodigo(RecuperarContrasenaModel codigoRecuperar) {
        RecuperarContrasenaEntity entity = Mapper.map(codigoRecuperar, RecuperarContrasenaEntity.class);
        return Mapper.map(recuperarContrasenaRepository.save(entity), RecuperarContrasenaModel.class);
    }

    @Override
    public RecuperarContrasenaModel consultarCodigosPorCorreo(String correo, LocalDateTime fechaActual) {
        RecuperarContrasenaEntity entity = recuperarContrasenaRepository.consultarCodigosPorCorreo(correo, fechaActual);
        return (Objects.nonNull(entity)) ? Mapper.map(entity, RecuperarContrasenaModel.class) : null;
    }
}
