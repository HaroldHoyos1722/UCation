package co.com.polijic.ucation.services;

import co.com.polijic.ucation.domain.exception.InfoException;
import co.com.polijic.ucation.domain.requesters.RegistroUsuarioRequester;
import co.com.polijic.ucation.usecases.ports.UsuarioPort;
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
class UsuarioServiceTest {

    @Mock
    private UsuarioPort usuarioPort;

    @InjectMocks
    private UsuarioService usuarioService;

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
    @DisplayName("registrarUsuario delega al port sin retornar valor")
    void registrarUsuario_datosValidos_delegaAlPort() throws Exception {
        doNothing().when(usuarioPort).registrarUsuario(any(RegistroUsuarioRequester.class));

        assertDoesNotThrow(() -> usuarioService.registrarUsuario(buildRequester()));

        verify(usuarioPort).registrarUsuario(any(RegistroUsuarioRequester.class));
    }

    @Test
    @DisplayName("registrarUsuario invoca el port exactamente una vez")
    void registrarUsuario_invocaPortUnaVez() throws Exception {
        doNothing().when(usuarioPort).registrarUsuario(any(RegistroUsuarioRequester.class));

        usuarioService.registrarUsuario(buildRequester());

        verify(usuarioPort, times(1)).registrarUsuario(any(RegistroUsuarioRequester.class));
    }

    @Test
    @DisplayName("registrarUsuario propaga InfoException cuando el correo ya existe")
    void registrarUsuario_correoYaExiste_propagaInfoException() throws Exception {
        doThrow(new InfoException("Ya existe un usuario con el correo electrónico ingresado"))
                .when(usuarioPort).registrarUsuario(any(RegistroUsuarioRequester.class));

        InfoException ex = assertThrows(InfoException.class,
                () -> usuarioService.registrarUsuario(buildRequester()));

        assertEquals("Ya existe un usuario con el correo electrónico ingresado", ex.getMessage());
    }

    @Test
    @DisplayName("registrarUsuario propaga InfoException cuando la identificación ya existe")
    void registrarUsuario_identificacionYaExiste_propagaInfoException() throws Exception {
        doThrow(new InfoException("Ya existe un usuario para el número de identificación ingresado"))
                .when(usuarioPort).registrarUsuario(any(RegistroUsuarioRequester.class));

        InfoException ex = assertThrows(InfoException.class,
                () -> usuarioService.registrarUsuario(buildRequester()));

        assertEquals("Ya existe un usuario para el número de identificación ingresado", ex.getMessage());
    }
}
