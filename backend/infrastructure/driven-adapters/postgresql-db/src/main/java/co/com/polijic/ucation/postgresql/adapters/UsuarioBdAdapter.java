package co.com.polijic.ucation.postgresql.adapters;

import co.com.polijic.ucation.domain.common.UsuarioModel;
import co.com.polijic.ucation.domain.repositories.UsuarioRepositoryPort;
import co.com.polijic.ucation.domain.util.Mapper;
import co.com.polijic.ucation.postgresql.entities.UsuarioEntity;
import co.com.polijic.ucation.postgresql.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioBdAdapter implements UsuarioRepositoryPort {

    private final UsuarioRepository usuarioRepository;

    public UsuarioBdAdapter(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UsuarioModel guardarUsuario(UsuarioModel usuario) {
        UsuarioEntity entity = Mapper.map(usuario, UsuarioEntity.class);
        return Mapper.map(usuarioRepository.save(entity), UsuarioModel.class);
    }

    @Override
    public UsuarioModel consultarUsuarioPorCorreo(String correo) {
        Optional<UsuarioEntity> entity = usuarioRepository.findById(correo);
        return entity.map(usuarioEntity -> Mapper.map(usuarioEntity, UsuarioModel.class)).orElse(null);
    }
}
