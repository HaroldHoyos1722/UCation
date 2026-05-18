package co.com.polijic.ucation.postgresql.repository;

import co.com.polijic.ucation.postgresql.entities.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, String> {
}
