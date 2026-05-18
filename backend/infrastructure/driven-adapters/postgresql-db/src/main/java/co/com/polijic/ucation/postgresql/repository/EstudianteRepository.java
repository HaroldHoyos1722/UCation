package co.com.polijic.ucation.postgresql.repository;

import co.com.polijic.ucation.postgresql.entities.EstudianteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<EstudianteEntity, Integer> {
}
