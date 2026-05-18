package co.com.polijic.ucation.controller;

import co.com.polijic.ucation.domain.common.GeneralResponse;
import co.com.polijic.ucation.domain.enums.TipoMensajeEnum;
import co.com.polijic.ucation.domain.exception.InfoException;
import co.com.polijic.ucation.domain.requesters.CambiarContrasenaRequester;
import co.com.polijic.ucation.domain.requesters.RecuperarContrasenaRequester;
import co.com.polijic.ucation.services.RecuperarContrasenaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static co.com.polijic.ucation.domain.enums.MensajeEnum.MENSAJE_CAMBIAR_CONTRASENA_OK;
import static co.com.polijic.ucation.domain.enums.MensajeEnum.MENSAJE_RECUERAR_CONTRASENA_OK;

@Validated
@RestController
@RequestMapping("/recuperar-contrasena")
@CrossOrigin("*")
public class RecuperarContrasenaController {

    private final RecuperarContrasenaService recuperarContrasenaService;

    public RecuperarContrasenaController(RecuperarContrasenaService recuperarContrasenaService) {
        this.recuperarContrasenaService = recuperarContrasenaService;
    }

    @PostMapping("/recuperar")
    public ResponseEntity<GeneralResponse<Boolean>> recuperarContrasena(
            @RequestBody RecuperarContrasenaRequester requester) throws InfoException {
        recuperarContrasenaService.recuperarContrasena(requester);

        return ResponseEntity.ok(GeneralResponse.<Boolean>builder()
                    .tipoMensaje(TipoMensajeEnum.OK.getValor())
                    .mensaje(MENSAJE_RECUERAR_CONTRASENA_OK.getValor())
                    .respuesta(Boolean.TRUE).build());
    }

    @PostMapping("/cambiar-contrasena")
    public ResponseEntity<GeneralResponse<Boolean>> cambiarContrasena(
            @RequestBody CambiarContrasenaRequester requester) throws Exception {
        recuperarContrasenaService.cambiarContrasena(requester);

        return ResponseEntity.ok(GeneralResponse.<Boolean>builder()
                .tipoMensaje(TipoMensajeEnum.OK.getValor())
                .mensaje(MENSAJE_CAMBIAR_CONTRASENA_OK.getValor())
                .respuesta(Boolean.TRUE).build());
    }
}
