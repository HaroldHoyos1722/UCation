package co.com.polijic.ucation.services;

import co.com.polijic.ucation.domain.common.DominioValorModel;
import co.com.polijic.ucation.usecases.ports.DominioPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DominioServiceTest {

    @Mock
    private DominioPort dominioPort;

    @InjectMocks
    private DominioService dominioService;

    // ── consultarDominiosPorNombreDominio ────────────────────────────────────

    @Test
    @DisplayName("consultarDominiosPorNombreDominio delega al port y retorna la lista")
    void consultarDominios_nombreValido_retornaLista() {
        List<DominioValorModel> esperado = List.of(
                DominioValorModel.builder().idDominio(1).nombreDominio("TIPO_USUARIO").valor("ESTUDIANTE").build(),
                DominioValorModel.builder().idDominio(2).nombreDominio("TIPO_USUARIO").valor("MENTOR").build()
        );

        when(dominioPort.consultarDominiosPorNombreDominio("TIPO_USUARIO")).thenReturn(esperado);

        List<DominioValorModel> resultado = dominioService.consultarDominiosPorNombreDominio("TIPO_USUARIO");

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("ESTUDIANTE", resultado.get(0).getValor());
        assertEquals("MENTOR", resultado.get(1).getValor());
    }

    @Test
    @DisplayName("consultarDominiosPorNombreDominio invoca el port exactamente una vez")
    void consultarDominios_invocaPortUnaVez() {
        when(dominioPort.consultarDominiosPorNombreDominio("TIPO_USUARIO"))
                .thenReturn(List.of(new DominioValorModel()));

        dominioService.consultarDominiosPorNombreDominio("TIPO_USUARIO");

        verify(dominioPort, times(1)).consultarDominiosPorNombreDominio("TIPO_USUARIO");
    }

    @Test
    @DisplayName("consultarDominiosPorNombreDominio retorna lista vacía cuando el dominio no tiene valores")
    void consultarDominios_sinResultados_retornaListaVacia() {
        when(dominioPort.consultarDominiosPorNombreDominio("DOMINIO_VACIO"))
                .thenReturn(Collections.emptyList());

        List<DominioValorModel> resultado = dominioService.consultarDominiosPorNombreDominio("DOMINIO_VACIO");

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("consultarDominiosPorNombreDominio retorna null cuando el port retorna null")
    void consultarDominios_portRetornaNull_retornaNull() {
        when(dominioPort.consultarDominiosPorNombreDominio("DOMINIO_NULL")).thenReturn(null);

        List<DominioValorModel> resultado = dominioService.consultarDominiosPorNombreDominio("DOMINIO_NULL");

        assertNull(resultado);
    }

    // ── consultarDominioValorPorNombreDominioAndValor ────────────────────────

    @Test
    @DisplayName("consultarDominioValorPorNombreDominioAndValor retorna null (pendiente de implementar)")
    void consultarDominioValor_retornaNull() {
        DominioValorModel resultado =
                dominioService.consultarDominioValorPorNombreDominioAndValor("TIPO_USUARIO", "ESTUDIANTE");

        assertNull(resultado);
        verifyNoInteractions(dominioPort);
    }
}
