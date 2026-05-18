package co.com.polijic.ucation.configuration;

import co.com.polijic.ucation.domain.common.GeneralResponse;
import co.com.polijic.ucation.domain.enums.TipoMensajeEnum;
import co.com.polijic.ucation.domain.exception.InfoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class HandlerException {

    @ExceptionHandler(InfoException.class)
    public ResponseEntity<GeneralResponse<String>> infoHandleException(InfoException ex) {
        log.error(ex.getMessage());
        GeneralResponse<String> response = GeneralResponse.<String>builder()
                .tipoMensaje(TipoMensajeEnum.ALERTA.getValor())
                .mensaje(ex.getMessage()).build();
        return ResponseEntity.ok().body(response);
    }
}
