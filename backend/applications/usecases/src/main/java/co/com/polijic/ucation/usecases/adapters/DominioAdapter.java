package co.com.polijic.ucation.usecases.adapters;

import co.com.polijic.ucation.domain.common.DominioValorModel;
import co.com.polijic.ucation.domain.repositories.DominioValorRepositoryPort;
import co.com.polijic.ucation.usecases.ports.DominioPort;

import java.util.List;

public class DominioAdapter implements DominioPort {

    private final DominioValorRepositoryPort dominioValorRepositoryPort;

    public DominioAdapter(DominioValorRepositoryPort dominioValorRepositoryPort) {
        this.dominioValorRepositoryPort = dominioValorRepositoryPort;
    }

    @Override
    public List<DominioValorModel> consultarDominiosPorNombreDominio(String nombreDominio) {
        return dominioValorRepositoryPort.consultarDominiosPorNombreDominio(nombreDominio);
    }

    @Override
    public DominioValorModel consultarDominioValorPorNombreDominioAndValor(String nombreDominio, String valor) {
        return null;
    }
}
