package co.com.polijic.ucation.services;

import co.com.polijic.ucation.domain.common.LoginModel;
import co.com.polijic.ucation.domain.exception.InfoException;
import co.com.polijic.ucation.domain.requesters.LoginRequester;
import co.com.polijic.ucation.usecases.ports.LoginPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    private LoginPort loginPort;

    @InjectMocks
    private LoginService loginService;

    // ── login ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("login delega al port y retorna el LoginModel")
    void login_credencialesValidas_retornaLoginModel() throws Exception {
        LoginRequester requester = LoginRequester.builder()
                .correo("usuario@correo.com")
                .contrasena("contrasena123")
                .build();

        LoginModel esperado = LoginModel.builder()
                .token("eyJhbGciOiJIUzI1NiJ9.token.firma")
                .nombre("Juan Pérez")
                .build();

        when(loginPort.login(any(LoginRequester.class))).thenReturn(esperado);

        LoginModel resultado = loginService.login(requester);

        assertNotNull(resultado);
        assertEquals("eyJhbGciOiJIUzI1NiJ9.token.firma", resultado.getToken());
        assertEquals("Juan Pérez", resultado.getNombre());
    }

    @Test
    @DisplayName("login invoca el port exactamente una vez")
    void login_invocaPortUnaVez() throws Exception {
        LoginRequester requester = LoginRequester.builder()
                .correo("usuario@correo.com")
                .contrasena("contrasena123")
                .build();

        when(loginPort.login(any(LoginRequester.class))).thenReturn(new LoginModel());

        loginService.login(requester);

        verify(loginPort, times(1)).login(requester);
    }

    @Test
    @DisplayName("login propaga InfoException cuando las credenciales son inválidas")
    void login_credencialesInvalidas_propagaInfoException() throws Exception {
        LoginRequester requester = LoginRequester.builder()
                .correo("usuario@correo.com")
                .contrasena("contrasenaMala")
                .build();

        when(loginPort.login(any(LoginRequester.class)))
                .thenThrow(new InfoException("El correo electrónico o la contraseña es incorrecto, vuelva a intentar"));

        InfoException ex = assertThrows(InfoException.class, () -> loginService.login(requester));

        assertEquals("El correo electrónico o la contraseña es incorrecto, vuelva a intentar", ex.getMessage());
    }

    @Test
    @DisplayName("login propaga InfoException cuando el usuario no está activo")
    void login_usuarioInactivo_propagaInfoException() throws Exception {
        LoginRequester requester = LoginRequester.builder()
                .correo("inactivo@correo.com")
                .contrasena("contrasena123")
                .build();

        when(loginPort.login(any(LoginRequester.class)))
                .thenThrow(new InfoException("El usuario no se encuentra activo, intente recuperando la contraseña."));

        InfoException ex = assertThrows(InfoException.class, () -> loginService.login(requester));

        assertEquals("El usuario no se encuentra activo, intente recuperando la contraseña.", ex.getMessage());
    }

    // ── logout ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("logout delega al port con el token correcto")
    void logout_tokenValido_delegaAlPort() {
        doNothing().when(loginPort).logout(anyString());

        loginService.logout("eyJhbGciOiJIUzI1NiJ9.token.firma");

        verify(loginPort).logout("eyJhbGciOiJIUzI1NiJ9.token.firma");
    }

    @Test
    @DisplayName("logout invoca el port exactamente una vez")
    void logout_invocaPortUnaVez() {
        doNothing().when(loginPort).logout(anyString());

        loginService.logout("eyJhbGciOiJIUzI1NiJ9.token.firma");

        verify(loginPort, times(1)).logout(anyString());
    }

    @Test
    @DisplayName("logout propaga la excepción del port cuando el token es inválido")
    void logout_tokenInvalido_propagaExcepcion() {
        doThrow(new RuntimeException("Token inválido o vencido"))
                .when(loginPort).logout(anyString());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> loginService.logout("tokeninvalido"));

        assertEquals("Token inválido o vencido", ex.getMessage());
    }
}
