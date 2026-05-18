package co.com.polijic.ucation.services;

import co.com.polijic.ucation.domain.common.DominioValorModel;
import co.com.polijic.ucation.usecases.ports.DominioPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DominioService {

    private final DominioPort dominioPort;

    public DominioService(DominioPort dominioPort) {
        this.dominioPort = dominioPort;
    }

    @Transactional(readOnly = true)
    public List<DominioValorModel> consultarDominiosPorNombreDominio(String nombreDominio) {
        return dominioPort.consultarDominiosPorNombreDominio(nombreDominio);
    }

    @Transactional(readOnly = true)
    public DominioValorModel consultarDominioValorPorNombreDominioAndValor(String nombreDominio, String valor) {
        return null;
    }
}
