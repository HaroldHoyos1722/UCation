package co.com.polijic.ucation.controller;

import co.com.polijic.ucation.domain.common.GeneralResponse;
import co.com.polijic.ucation.domain.enums.TipoMensajeEnum;
import co.com.polijic.ucation.domain.requesters.RegistroUsuarioRequester;
import co.com.polijic.ucation.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static co.com.polijic.ucation.domain.enums.MensajeEnum.USUARIO_REGISTRADO_CON_EXITO;

@Validated
@CrossOrigin("*")
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<GeneralResponse<Boolean>> registrarUsuario(
            @Valid @RequestBody RegistroUsuarioRequester requester) throws Exception {
        usuarioService.registrarUsuario(requester);
        return ResponseEntity.ok(GeneralResponse.<Boolean>builder()
                .tipoMensaje(TipoMensajeEnum.OK.getValor())
                .mensaje(USUARIO_REGISTRADO_CON_EXITO.getValor())
                .respuesta(true).build());
    }
}
