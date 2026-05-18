package co.com.polijic.ucation.controller;

import co.com.polijic.ucation.domain.common.GeneralResponse;
import co.com.polijic.ucation.domain.exception.InfoException;
import co.com.polijic.ucation.domain.requesters.CambiarContrasenaRequester;
import co.com.polijic.ucation.domain.requesters.RecuperarContrasenaRequester;
import co.com.polijic.ucation.services.RecuperarContrasenaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecuperarContrasenaControllerTest {

    @Mock
    private RecuperarContrasenaService recuperarContrasenaService;

    @InjectMocks
    private RecuperarContrasenaController recuperarContrasenaController;

    // ── recuperarContrasena ──────────────────────────────────────────────────

    @Test
    @DisplayName("Recuperar contraseña con correo válido retorna 200 con respuesta true")
    void recuperarContrasena_correoValido_retorna200() throws InfoException {
        RecuperarContrasenaRequester requester = RecuperarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .build();

        doNothing().when(recuperarContrasenaService)
                .recuperarContrasena(any(RecuperarContrasenaRequester.class));

        ResponseEntity<GeneralResponse<Boolean>> response =
                recuperarContrasenaController.recuperarContrasena(requester);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getRespuesta());
        assertEquals("Si el correo electrónico es correcto, se le enviará un código de recuperación.",
                response.getBody().getMensaje());
        assertEquals("OK", response.getBody().getTipoMensaje());
    }

    @Test
    @DisplayName("Recuperar contraseña invoca el servicio exactamente una vez")
    void recuperarContrasena_invocaServicioUnaVez() throws InfoException {
        RecuperarContrasenaRequester requester = RecuperarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .build();

        doNothing().when(recuperarContrasenaService)
                .recuperarContrasena(any(RecuperarContrasenaRequester.class));

        recuperarContrasenaController.recuperarContrasena(requester);

        verify(recuperarContrasenaService, times(1)).recuperarContrasena(requester);
    }

    @Test
    @DisplayName("Recuperar contraseña cuando el servicio lanza InfoException la propaga")
    void recuperarContrasena_servicioLanzaInfoException_propagaExcepcion() throws InfoException {
        RecuperarContrasenaRequester requester = RecuperarContrasenaRequester.builder()
                .correo("noexiste@correo.com")
                .build();

        doThrow(new InfoException("Correo no encontrado"))
                .when(recuperarContrasenaService)
                .recuperarContrasena(any(RecuperarContrasenaRequester.class));

        InfoException ex = assertThrows(InfoException.class,
                () -> recuperarContrasenaController.recuperarContrasena(requester));

        assertEquals("Correo no encontrado", ex.getMessage());
    }

    // ── cambiarContrasena ────────────────────────────────────────────────────

    @Test
    @DisplayName("Cambiar contraseña con datos válidos retorna 200 con respuesta true")
    void cambiarContrasena_datosValidos_retorna200() throws Exception {
        CambiarContrasenaRequester requester = CambiarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .codigo("123456")
                .contrasena("nuevaContrasena123")
                .build();

        doNothing().when(recuperarContrasenaService)
                .cambiarContrasena(any(CambiarContrasenaRequester.class));

        ResponseEntity<GeneralResponse<Boolean>> response =
                recuperarContrasenaController.cambiarContrasena(requester);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getRespuesta());
        assertEquals("La contraseña se ha cambiado con éxito.", response.getBody().getMensaje());
        assertEquals("OK", response.getBody().getTipoMensaje());
    }

    @Test
    @DisplayName("Cambiar contraseña invoca el servicio exactamente una vez")
    void cambiarContrasena_invocaServicioUnaVez() throws Exception {
        CambiarContrasenaRequester requester = CambiarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .codigo("123456")
                .contrasena("nuevaContrasena123")
                .build();

        doNothing().when(recuperarContrasenaService)
                .cambiarContrasena(any(CambiarContrasenaRequester.class));

        recuperarContrasenaController.cambiarContrasena(requester);

        verify(recuperarContrasenaService, times(1)).cambiarContrasena(requester);
    }

    @Test
    @DisplayName("Cambiar contraseña con código vencido propaga la excepción del servicio")
    void cambiarContrasena_codigoVencido_propagaExcepcion() throws Exception {
        CambiarContrasenaRequester requester = CambiarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .codigo("000000")
                .contrasena("nuevaContrasena123")
                .build();

        doThrow(new InfoException("El código de recuperación ha expirado, genere un nuevo código y vuelva a intentar"))
                .when(recuperarContrasenaService)
                .cambiarContrasena(any(CambiarContrasenaRequester.class));

        InfoException ex = assertThrows(InfoException.class,
                () -> recuperarContrasenaController.cambiarContrasena(requester));

        assertEquals("El código de recuperación ha expirado, genere un nuevo código y vuelva a intentar",
                ex.getMessage());
    }

    @Test
    @DisplayName("Cambiar contraseña con código erróneo propaga la excepción del servicio")
    void cambiarContrasena_codigoErroneo_propagaExcepcion() throws Exception {
        CambiarContrasenaRequester requester = CambiarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .codigo("999999")
                .contrasena("nuevaContrasena123")
                .build();

        doThrow(new InfoException("El código de recuperación es incorrecto, vuelva a intentar"))
                .when(recuperarContrasenaService)
                .cambiarContrasena(any(CambiarContrasenaRequester.class));

        InfoException ex = assertThrows(InfoException.class,
                () -> recuperarContrasenaController.cambiarContrasena(requester));

        assertEquals("El código de recuperación es incorrecto, vuelva a intentar", ex.getMessage());
    }
}
