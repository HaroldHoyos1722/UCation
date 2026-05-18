package co.com.polijic.ucation.controller;

import co.com.polijic.ucation.domain.common.GeneralResponse;
import co.com.polijic.ucation.domain.common.LoginModel;
import co.com.polijic.ucation.domain.requesters.LoginRequester;
import co.com.polijic.ucation.services.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static co.com.polijic.ucation.domain.enums.MensajeEnum.DATOS_VALIDAOS_CON_EXITO;

@Validated
@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<GeneralResponse<LoginModel>> login(@RequestBody LoginRequester requester) throws Exception {
        LoginModel login = loginService.login(requester);

        return ResponseEntity.ok(GeneralResponse.<LoginModel>builder()
                .respuesta(login)
                .mensaje(DATOS_VALIDAOS_CON_EXITO.getValor()).build());
    }

    @PostMapping("/logout")
    public ResponseEntity<GeneralResponse<Void>> logout(@RequestHeader("Authorization") String authHeader) {
        loginService.logout(authHeader.substring(7));

        return ResponseEntity.ok(GeneralResponse.<Void>builder()
                .mensaje("Sesión cerrada correctamente").build());
    }
}
