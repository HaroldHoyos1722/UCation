package co.com.polijic.ucation.postgresql.adapters;

import co.com.polijic.ucation.domain.common.DominioValorModel;
import co.com.polijic.ucation.domain.repositories.DominioValorRepositoryPort;
import co.com.polijic.ucation.domain.util.Mapper;
import co.com.polijic.ucation.postgresql.entities.DominioValorEntity;
import co.com.polijic.ucation.postgresql.repository.DominioValorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DominioValorBdAdapter implements DominioValorRepositoryPort {

    private final DominioValorRepository dominioValorRepository;

    public DominioValorBdAdapter(DominioValorRepository dominioValorRepository) {
        this.dominioValorRepository = dominioValorRepository;
    }

    @Override
    public DominioValorModel consultarDominioPorId(Integer idDominio) {
        Optional<DominioValorEntity> entity = dominioValorRepository.findById(idDominio);
        return entity.map(d -> Mapper.map(d, DominioValorModel.class)).orElse(null);
    }

    @Override
    public List<DominioValorModel> consultarDominiosPorNombreDominio(String nombreDominio) {
        List<DominioValorEntity> dominios = dominioValorRepository.findByNombreDominio(nombreDominio);
        return (Objects.nonNull(dominios)) ? Mapper.mapAll(dominios, DominioValorModel.class) : null;
    }

    @Override
    public DominioValorModel consultarDominioValorPorNombreDominioAndCodigo(String nombreDominio, String codigo) {
        DominioValorEntity dominio = dominioValorRepository.findByNombreDominioAndCodigo(nombreDominio, codigo);
        return (Objects.nonNull(dominio)) ? Mapper.map(dominio, DominioValorModel.class) : null;
    }
}
