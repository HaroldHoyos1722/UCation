package co.com.polijic.ucation.controller;

import co.com.polijic.ucation.domain.common.GeneralResponse;
import co.com.polijic.ucation.domain.common.LoginModel;
import co.com.polijic.ucation.domain.exception.InfoException;
import co.com.polijic.ucation.domain.requesters.LoginRequester;
import co.com.polijic.ucation.services.LoginService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private LoginController loginController;

    @Test
    @DisplayName("Login con credenciales válidas retorna 200 con token y nombre")
    void login_credencialesValidas_retorna200() throws Exception {
        LoginRequester requester = LoginRequester.builder()
                .correo("usuario@correo.com")
                .contrasena("contrasena123")
                .build();

        LoginModel loginModel = LoginModel.builder()
                .token("eyJhbGciOiJIUzI1NiJ9.token.firma")
                .nombre("Juan Pérez")
                .build();

        when(loginService.login(any(LoginRequester.class))).thenReturn(loginModel);

        ResponseEntity<GeneralResponse<LoginModel>> response = loginController.login(requester);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("eyJhbGciOiJIUzI1NiJ9.token.firma", response.getBody().getRespuesta().getToken());
        assertEquals("Juan Pérez", response.getBody().getRespuesta().getNombre());
        assertEquals("Los datos han sido validados con éxito.", response.getBody().getMensaje());
        verify(loginService).login(requester);
    }

    @Test
    @DisplayName("Login llama al servicio exactamente una vez")
    void login_invocaServicioUnaVez() throws Exception {
        LoginRequester requester = LoginRequester.builder()
                .correo("usuario@correo.com")
                .contrasena("contrasena123")
                .build();

        when(loginService.login(any(LoginRequester.class))).thenReturn(new LoginModel());

        loginController.login(requester);

        verify(loginService, times(1)).login(requester);
    }

    @Test
    @DisplayName("Login con credenciales inválidas propaga InfoException")
    void login_credencialesInvalidas_propagaInfoException() throws Exception {
        LoginRequester requester = LoginRequester.builder()
                .correo("usuario@correo.com")
                .contrasena("contrasenaMala")
                .build();

        when(loginService.login(any(LoginRequester.class)))
                .thenThrow(new InfoException("El correo electrónico o la contraseña es incorrecto, vuelva a intentar"));

        InfoException ex = assertThrows(InfoException.class, () -> loginController.login(requester));

        assertEquals("El correo electrónico o la contraseña es incorrecto, vuelva a intentar", ex.getMessage());
    }

    @Test
    @DisplayName("Logout con token válido retorna 200 con mensaje de cierre de sesión")
    void logout_tokenValido_retorna200() throws Exception {
        doNothing().when(loginService).logout(anyString());

        ResponseEntity<GeneralResponse<Void>> response =
                loginController.logout("Bearer eyJhbGciOiJIUzI1NiJ9.token.firma");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Sesión cerrada correctamente", response.getBody().getMensaje());
    }

    @Test
    @DisplayName("Logout extrae correctamente el token del header Authorization")
    void logout_extraeTokenDelHeader() throws Exception {
        doNothing().when(loginService).logout(anyString());

        loginController.logout("Bearer eyJhbGciOiJIUzI1NiJ9.token.firma");

        verify(loginService).logout("eyJhbGciOiJIUzI1NiJ9.token.firma");
    }

    @Test
    @DisplayName("Logout llama al servicio exactamente una vez")
    void logout_invocaServicioUnaVez() throws Exception {
        doNothing().when(loginService).logout(anyString());

        loginController.logout("Bearer eyJhbGciOiJIUzI1NiJ9.token.firma");

        verify(loginService, times(1)).logout(anyString());
    }

    @Test
    @DisplayName("Logout con token inválido propaga la excepción del servicio")
    void logout_servicioLanzaExcepcion_propagaExcepcion() {
        doThrow(new RuntimeException("Token inválido o vencido"))
                .when(loginService).logout(anyString());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> loginController.logout("Bearer tokeninvalido"));

        assertEquals("Token inválido o vencido", ex.getMessage());
    }
}
