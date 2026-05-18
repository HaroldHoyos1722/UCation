package co.com.polijic.ucation.postgresql.repository;

import co.com.polijic.ucation.postgresql.entities.SesionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends JpaRepository<SesionEntity, String> {
}
