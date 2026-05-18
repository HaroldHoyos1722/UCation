package co.com.polijic.ucation.controller;

import co.com.polijic.ucation.domain.common.DominioValorModel;
import co.com.polijic.ucation.domain.common.GeneralResponse;
import co.com.polijic.ucation.services.DominioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DominioControllerTest {

    @Mock
    private DominioService dominioService;

    @InjectMocks
    private DominioController dominioController;

    @Test
    @DisplayName("Consultar dominios con resultados retorna 200 con la lista")
    void consultarDominios_conResultados_retorna200() {
        List<DominioValorModel> dominios = List.of(
                DominioValorModel.builder()
                        .idDominio(1)
                        .nombreDominio("TIPO_USUARIO")
                        .valor("ESTUDIANTE")
                        .codigo("1")
                        .activo("S")
                        .build(),
                DominioValorModel.builder()
                        .idDominio(2)
                        .nombreDominio("TIPO_USUARIO")
                        .valor("MENTOR")
                        .codigo("2")
                        .activo("S")
                        .build()
        );

        when(dominioService.consultarDominiosPorNombreDominio("TIPO_USUARIO")).thenReturn(dominios);

        ResponseEntity<GeneralResponse<List<DominioValorModel>>> response =
                dominioController.consultarDominiosPorNombreDominio("TIPO_USUARIO");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getRespuesta().size());
        assertEquals("Información consultada con éxito.", response.getBody().getMensaje());
        assertEquals("OK", response.getBody().getTipoMensaje());
    }

    @Test
    @DisplayName("Consultar dominios con lista vacía retorna 204 No Content")
    void consultarDominios_listaVacia_retorna204() {
        when(dominioService.consultarDominiosPorNombreDominio("DOMINIO_INEXISTENTE"))
                .thenReturn(Collections.emptyList());

        ResponseEntity<GeneralResponse<List<DominioValorModel>>> response =
                dominioController.consultarDominiosPorNombreDominio("DOMINIO_INEXISTENTE");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Consultar dominios con resultado null retorna 204 No Content")
    void consultarDominios_resultadoNull_retorna204() {
        when(dominioService.consultarDominiosPorNombreDominio("DOMINIO_NULL")).thenReturn(null);

        ResponseEntity<GeneralResponse<List<DominioValorModel>>> response =
                dominioController.consultarDominiosPorNombreDominio("DOMINIO_NULL");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    @DisplayName("Consultar dominios invoca el servicio exactamente una vez")
    void consultarDominios_invocaServicioUnaVez() {
        when(dominioService.consultarDominiosPorNombreDominio("TIPO_USUARIO"))
                .thenReturn(List.of(new DominioValorModel()));

        dominioController.consultarDominiosPorNombreDominio("TIPO_USUARIO");

        verify(dominioService, times(1)).consultarDominiosPorNombreDominio("TIPO_USUARIO");
    }

    @Test
    @DisplayName("Consultar dominio por nombre y valor retorna el modelo correctamente")
    void consultarDominioValor_existente_retornaDominioValorModel() {
        DominioValorModel modelo = DominioValorModel.builder()
                .idDominio(1)
                .nombreDominio("TIPO_USUARIO")
                .valor("ESTUDIANTE")
                .codigo("1")
                .activo("S")
                .build();

        when(dominioService.consultarDominioValorPorNombreDominioAndValor("TIPO_USUARIO", "ESTUDIANTE"))
                .thenReturn(modelo);

        DominioValorModel resultado =
                dominioController.consultarDominioValorPorNombreDominioAndValor("TIPO_USUARIO", "ESTUDIANTE");

        assertNotNull(resultado);
        assertEquals("TIPO_USUARIO", resultado.getNombreDominio());
        assertEquals("ESTUDIANTE", resultado.getValor());
        assertEquals("1", resultado.getCodigo());
    }

    @Test
    @DisplayName("Consultar dominio por nombre y valor invoca el servicio exactamente una vez")
    void consultarDominioValor_invocaServicioUnaVez() {
        when(dominioService.consultarDominioValorPorNombreDominioAndValor(anyString(), anyString()))
                .thenReturn(new DominioValorModel());

        dominioController.consultarDominioValorPorNombreDominioAndValor("TIPO_USUARIO", "ESTUDIANTE");

        verify(dominioService, times(1))
                .consultarDominioValorPorNombreDominioAndValor("TIPO_USUARIO", "ESTUDIANTE");
    }
}
