package co.com.polijic.ucation.services;

import co.com.polijic.ucation.domain.exception.InfoException;
import co.com.polijic.ucation.domain.requesters.CambiarContrasenaRequester;
import co.com.polijic.ucation.domain.requesters.RecuperarContrasenaRequester;
import co.com.polijic.ucation.usecases.ports.RecuperarContrasenaPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecuperarContrasenaServiceTest {

    @Mock
    private RecuperarContrasenaPort recuperarContrasenaPort;

    @InjectMocks
    private RecuperarContrasenaService recuperarContrasenaService;

    // ── recuperarContrasena ──────────────────────────────────────────────────

    @Test
    @DisplayName("recuperarContrasena delega al port sin retornar valor")
    void recuperarContrasena_correoValido_delegaAlPort() throws InfoException {
        RecuperarContrasenaRequester requester = RecuperarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .build();

        doNothing().when(recuperarContrasenaPort)
                .recuperarContrasena(any(RecuperarContrasenaRequester.class));

        assertDoesNotThrow(() -> recuperarContrasenaService.recuperarContrasena(requester));

        verify(recuperarContrasenaPort).recuperarContrasena(requester);
    }

    @Test
    @DisplayName("recuperarContrasena invoca el port exactamente una vez")
    void recuperarContrasena_invocaPortUnaVez() throws InfoException {
        RecuperarContrasenaRequester requester = RecuperarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .build();

        doNothing().when(recuperarContrasenaPort)
                .recuperarContrasena(any(RecuperarContrasenaRequester.class));

        recuperarContrasenaService.recuperarContrasena(requester);

        verify(recuperarContrasenaPort, times(1)).recuperarContrasena(requester);
    }

    @Test
    @DisplayName("recuperarContrasena propaga InfoException del port")
    void recuperarContrasena_portLanzaInfoException_propagaExcepcion() throws InfoException {
        RecuperarContrasenaRequester requester = RecuperarContrasenaRequester.builder()
                .correo("noexiste@correo.com")
                .build();

        doThrow(new InfoException("Correo no encontrado"))
                .when(recuperarContrasenaPort)
                .recuperarContrasena(any(RecuperarContrasenaRequester.class));

        InfoException ex = assertThrows(InfoException.class,
                () -> recuperarContrasenaService.recuperarContrasena(requester));

        assertEquals("Correo no encontrado", ex.getMessage());
    }

    // ── cambiarContrasena ────────────────────────────────────────────────────

    @Test
    @DisplayName("cambiarContrasena delega al port sin retornar valor")
    void cambiarContrasena_datosValidos_delegaAlPort() throws Exception {
        CambiarContrasenaRequester requester = CambiarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .codigo("123456")
                .contrasena("nuevaContrasena123")
                .build();

        doNothing().when(recuperarContrasenaPort)
                .cambiarContrasena(any(CambiarContrasenaRequester.class));

        assertDoesNotThrow(() -> recuperarContrasenaService.cambiarContrasena(requester));

        verify(recuperarContrasenaPort).cambiarContrasena(requester);
    }

    @Test
    @DisplayName("cambiarContrasena invoca el port exactamente una vez")
    void cambiarContrasena_invocaPortUnaVez() throws Exception {
        CambiarContrasenaRequester requester = CambiarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .codigo("123456")
                .contrasena("nuevaContrasena123")
                .build();

        doNothing().when(recuperarContrasenaPort)
                .cambiarContrasena(any(CambiarContrasenaRequester.class));

        recuperarContrasenaService.cambiarContrasena(requester);

        verify(recuperarContrasenaPort, times(1)).cambiarContrasena(requester);
    }

    @Test
    @DisplayName("cambiarContrasena propaga InfoException cuando el código está vencido")
    void cambiarContrasena_codigoVencido_propagaInfoException() throws Exception {
        CambiarContrasenaRequester requester = CambiarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .codigo("000000")
                .contrasena("nuevaContrasena123")
                .build();

        doThrow(new InfoException("El código de recuperación ha expirado, genere un nuevo código y vuelva a intentar"))
                .when(recuperarContrasenaPort)
                .cambiarContrasena(any(CambiarContrasenaRequester.class));

        InfoException ex = assertThrows(InfoException.class,
                () -> recuperarContrasenaService.cambiarContrasena(requester));

        assertEquals("El código de recuperación ha expirado, genere un nuevo código y vuelva a intentar",
                ex.getMessage());
    }

    @Test
    @DisplayName("cambiarContrasena propaga InfoException cuando el código es erróneo")
    void cambiarContrasena_codigoErroneo_propagaInfoException() throws Exception {
        CambiarContrasenaRequester requester = CambiarContrasenaRequester.builder()
                .correo("usuario@correo.com")
                .codigo("999999")
                .contrasena("nuevaContrasena123")
                .build();

        doThrow(new InfoException("El código de recuperación es incorrecto, vuelva a intentar"))
                .when(recuperarContrasenaPort)
                .cambiarContrasena(any(CambiarContrasenaRequester.class));

        InfoException ex = assertThrows(InfoException.class,
                () -> recuperarContrasenaService.cambiarContrasena(requester));

        assertEquals("El código de recuperación es incorrecto, vuelva a intentar", ex.getMessage());
    }
}
