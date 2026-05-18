package co.com.polijic.ucation.usecases.ports;

import co.com.polijic.ucation.domain.common.DominioValorModel;

import java.util.List;

public interface DominioPort {

    List<DominioValorModel> consultarDominiosPorNombreDominio(String nombreDominio);

    DominioValorModel consultarDominioValorPorNombreDominioAndValor(String nombreDominio, String valor);
}
