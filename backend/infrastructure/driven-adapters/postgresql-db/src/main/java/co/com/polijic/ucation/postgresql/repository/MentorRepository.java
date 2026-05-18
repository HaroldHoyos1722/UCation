package co.com.polijic.ucation.postgresql.repository;

import co.com.polijic.ucation.postgresql.entities.MentorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorRepository extends JpaRepository<MentorEntity, Integer> {
}
