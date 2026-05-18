package co.com.polijic.ucation.postgresql.repository;

import co.com.polijic.ucation.postgresql.entities.RecuperarContrasenaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RecuperarContrasenaRepository extends JpaRepository<RecuperarContrasenaEntity, Long> {

    @Query("SELECT r FROM RecuperarContrasenaEntity r WHERE r.correo = :correo AND r.fechaFin > :fechaActual")
    RecuperarContrasenaEntity consultarCodigosPorCorreo(String correo, LocalDateTime fechaActual);
}
