package co.com.polijic.ucation.domain.repositories;

import co.com.polijic.ucation.domain.common.DominioValorModel;

import java.util.List;

public interface DominioValorRepositoryPort {

    DominioValorModel consultarDominioPorId(Integer idDominio);
    List<DominioValorModel> consultarDominiosPorNombreDominio(String nombreDominio);
    DominioValorModel consultarDominioValorPorNombreDominioAndCodigo(String nombreDominio, String valor);
}
