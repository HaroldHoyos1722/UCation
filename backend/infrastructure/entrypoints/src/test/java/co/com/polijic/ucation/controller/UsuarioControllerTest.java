package co.com.polijic.ucation.controller;

import co.com.polijic.ucation.domain.common.GeneralResponse;
import co.com.polijic.ucation.domain.requesters.RegistroUsuarioRequester;
import co.com.polijic.ucation.services.UsuarioService;
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
class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private RegistroUsuarioRequester buildRequester() {
        return RegistroUsuarioRequester.builder()
                .tipoUsuario(1)
                .nombre("Juan")
                .apellido("Pérez")
                .tipoIdentificacion(1)
                .numeroIdentificacion("123456789")
                .correo("juan@correo.com")
                .contrasena("contrasena123")
                .genero(1)
                .build();
    }

    @Test
    @DisplayName("Registrar usuario con datos válidos retorna 200 con respuesta true")
    void registrarUsuario_datosValidos_retorna200ConTrue() throws Exception {
        doNothing().when(usuarioService).registrarUsuario(any(RegistroUsuarioRequester.class));

        ResponseEntity<GeneralResponse<Boolean>> response =
                usuarioController.registrarUsuario(buildRequester());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getRespuesta());
        assertEquals("Usuario registrado con éxito.", response.getBody().getMensaje());
        assertEquals("OK", response.getBody().getTipoMensaje());
    }

    @Test
    @DisplayName("Registrar usuario invoca el servicio exactamente una vez")
    void registrarUsuario_invocaServicioUnaVez() throws Exception {
        doNothing().when(usuarioService).registrarUsuario(any(RegistroUsuarioRequester.class));

        usuarioController.registrarUsuario(buildRequester());

        verify(usuarioService, times(1)).registrarUsuario(any(RegistroUsuarioRequester.class));
    }

    @Test
    @DisplayName("Registrar usuario con correo ya existente propaga la excepción del servicio")
    void registrarUsuario_correoYaExiste_propagaExcepcion() throws Exception {
        doThrow(new Exception("Ya existe un usuario con el correo electrónico ingresado"))
                .when(usuarioService).registrarUsuario(any(RegistroUsuarioRequester.class));

        Exception ex = assertThrows(Exception.class,
                () -> usuarioController.registrarUsuario(buildRequester()));

        assertEquals("Ya existe un usuario con el correo electrónico ingresado", ex.getMessage());
    }

    @Test
    @DisplayName("Registrar usuario con identificación ya existente propaga la excepción del servicio")
    void registrarUsuario_identificacionYaExiste_propagaExcepcion() throws Exception {
        doThrow(new Exception("Ya existe un usuario para el número de identificación ingresado"))
                .when(usuarioService).registrarUsuario(any(RegistroUsuarioRequester.class));

        Exception ex = assertThrows(Exception.class,
                () -> usuarioController.registrarUsuario(buildRequester()));

        assertEquals("Ya existe un usuario para el número de identificación ingresado", ex.getMessage());
    }
}
