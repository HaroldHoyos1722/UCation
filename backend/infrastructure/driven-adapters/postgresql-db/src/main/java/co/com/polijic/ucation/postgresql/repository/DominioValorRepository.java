package co.com.polijic.ucation.postgresql.repository;

import co.com.polijic.ucation.postgresql.entities.DominioValorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DominioValorRepository extends JpaRepository<DominioValorEntity, Integer> {

    List<DominioValorEntity> findByNombreDominio(String nombreDominio);

    DominioValorEntity findByNombreDominioAndCodigo(String nombreDominio, String valor);
}
